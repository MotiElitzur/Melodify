package motiapps.melodify.common.permissions.data

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.permissions.domain.repository.PermissionRepository
import motiapps.melodify.common.permissions.di.ActivityContextProvider
import motiapps.melodify.common.permissions.di.PermissionResultCallback
import motiapps.melodify.common.permissions.domain.model.PermissionResult
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PermissionRepositoryImpl @Inject constructor(
    private val activityContextProvider: ActivityContextProvider,
    private val preferencesUseCases: PreferencesUseCases
): PermissionRepository {

    private val mutex = Mutex()

    override suspend fun checkPermission(permission: String): PermissionResult {
        val activity = activityContextProvider.activity
        return when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                PermissionResult.Granted
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                PermissionResult.Denied
            }
            isPermissionBlocked(permission) -> {
                PermissionResult.Blocked
            }
            else -> {
                PermissionResult.NotRequested
            }
        }
    }

    private suspend fun isPermissionBlocked(permission: String): Boolean {
        val result = preferencesUseCases.getPreferenceUseCase(PreferenceObject(permission, false))
        return (result as? Resource.Success)?.data as? Boolean ?: false
    }

    override suspend fun requestPermission(permission: String): PermissionResult = suspendCoroutine { cont ->
        val activity = activityContextProvider.activity

        activityContextProvider.requestPermission(permission, object : PermissionResultCallback {
            override fun onPermissionResult(permission: String, isGranted: Boolean) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (isGranted) {
                        cont.resume(PermissionResult.Granted)
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                            cont.resume(PermissionResult.Denied)
                        } else {
                            preferencesUseCases.setPreferenceUseCase(PreferenceObject(permission, true))
                            cont.resume(PermissionResult.Blocked)
                        }
                    }
                }
            }
        })
    }
}


