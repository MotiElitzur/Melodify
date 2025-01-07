package motiapps.melodify.common.permissions.data

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.permissions.domain.repository.PermissionRepository
import motiapps.melodify.common.permissions.domain.model.PermissionResult
import motiapps.melodify.common.permissions.domain.model.PermissionResultCallback
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PermissionRepositoryImpl @Inject constructor(
    private val permissionManager: PermissionManager,
    private val preferencesUseCases: PreferencesUseCases
) : PermissionRepository {

    override suspend fun checkPermission(permission: String): PermissionResult {
        val activity = permissionManager.activity ?: throw IllegalStateException("Activity is not available")
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
        val isBlocked = (result as? Resource.Success)?.data as? Boolean ?: false
        return isBlocked
    }

    override suspend fun requestPermission(permission: String): PermissionResult = suspendCoroutine { cont ->
        permissionManager.requestPermission(permission, object : PermissionResultCallback {
            override fun onPermissionResult(permission: String, isGranted: Boolean) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (isGranted) {
                        cont.resume(PermissionResult.Granted)
                    } else {
                        val activity = permissionManager.activity ?: throw IllegalStateException("Activity is not available")
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