package motiapps.melodify.core.data.lifecycle

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.Logger
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@ActivityRetainedScoped
class ActivityContextProvider @Inject constructor() : DefaultLifecycleObserver {

    private val activityRef = AtomicReference<ComponentActivity>()
    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    val activity: ComponentActivity
        get() = activityRef.get() ?: throw IllegalStateException("Activity is not available")

    override fun onCreate(owner: LifecycleOwner) {
        val activity = owner as? ComponentActivity ?: return
        activityRef.set(activity)

        // Register the ActivityResultLauncher early, you must to register here before onResume.
        requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Logger.log("Permission result: $isGranted")
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        activityRef.set(null)
        requestPermissionLauncher = null
    }

    /**
     * Launch a permission request with a callback to handle the result.
     */
    fun launchPermissionRequest(permission: String, callback: (Boolean) -> Unit) {
        requestPermissionLauncher?.launch(permission)
            ?: throw IllegalStateException("Permission launcher not registered. Ensure it is initialized in onCreate.")
    }
}