package motiapps.melodify.common.permissions.data

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import motiapps.melodify.common.Logger
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

    private val blockedPermissions = mutableSetOf<String>()

    override suspend fun checkPermission(permission: String): PermissionResult {
        val activity = permissionManager.activity ?: throw IllegalStateException("Activity is not available")
        val permissionResult =  when {
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

        Logger.log("PermissionRepository: Permission '$permission' check result: $permissionResult")
        return permissionResult
    }

    override suspend fun requestPermission(permission: String): PermissionResult = suspendCoroutine { cont ->
            try {
                permissionManager.requestPermission(permission, object : PermissionResultCallback {
                    override fun onPermissionResult(permission: String, isGranted: Boolean) {
                        // Handle permission result and determine result type
                        val result = if (isGranted) {
                            PermissionResult.Granted
                        } else {
                            val activity = permissionManager.activity ?: run {
                                cont.resumeWith(Result.failure(IllegalStateException("Activity is not available")))
                                return
                            }
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                                Logger.log("PermissionRepository: '$permission' denied by user, rationale shown")
                                PermissionResult.Denied
                            } else {
                                Logger.log("PermissionRepository: '$permission' denied and blocked, saving state")
                                CoroutineScope(Dispatchers.IO).launch {
                                    preferencesUseCases.setPreferenceUseCase(PreferenceObject(permission, true))
                                }
                                blockedPermissions.add(permission)
                                PermissionResult.Blocked
                            }
                        }
                        Logger.log("PermissionRepository: Result received for '$permission', granted: $isGranted, result: $result")
                        cont.resume(result) // Resume the coroutine with the permission result
                    }

                    override fun onActivityResumed() {
                        // Handle any permissions that were previously blocked
                        if (blockedPermissions.isNotEmpty()) {
                            CoroutineScope(Dispatchers.Default).launch {
                                blockedPermissions.toList().forEach { permission ->
                                    if (checkPermission(permission) is PermissionResult.Granted) {
                                        blockedPermissions.remove(permission)
                                        preferencesUseCases.removePreferenceUseCase(permission)
                                        Logger.log("PermissionRepository: '$permission' is now granted, removing from blocked permissions")
                                    }
                                }
                            }
                        }
                    }
                })
            } catch (e: Exception) {
                Logger.log("PermissionRepository: Exception during permission request: ${e.message}")
                cont.resumeWith(Result.failure(e))
            }

    }

    // region private functions

    private suspend fun isPermissionBlocked(permission: String): Boolean {
        val result = preferencesUseCases.getPreferenceUseCase(PreferenceObject(permission, false))
        return (result as? Resource.Success)?.data as? Boolean ?: false
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", permissionManager.activity?.packageName, null)
        }
        permissionManager.activity?.startActivity(intent)
    }

    // endregion
}