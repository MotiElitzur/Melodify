package motiapps.melodify.core.data.lifecycle

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.Logger
import motiapps.melodify.common.language.data.LanguageManager
import motiapps.melodify.common.permissions.data.PermissionManager
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@ActivityRetainedScoped
class ActivityContextProvider @Inject constructor(
    private val permissionManager: PermissionManager, // Injected as a dependency
    private val languageManager: LanguageManager
) : DefaultLifecycleObserver {

    private val activityRef = AtomicReference<ComponentActivity>()
    private val listeners = mutableListOf<LifecycleListener>()

    val activity: ComponentActivity
        get() = activityRef.get() ?: throw IllegalStateException("Activity is not available")

    init {
        // Register PermissionManager as a listener
        Logger.log("ActivityContextProvider: Registering PermissionManager as listener")
        preRegisterListener(permissionManager)
        // Register LanguageManager as a listener
        Logger.log("ActivityContextProvider: Registering LanguageManager as listener")
        preRegisterListener(languageManager)
    }

    /**
     * Register a listener early in the lifecycle.
     */
    private fun preRegisterListener(listener: LifecycleListener) {
        listeners.add(listener)
    }

    override fun onCreate(owner: LifecycleOwner) {
        val activity = owner as? ComponentActivity ?: return
        activityRef.set(activity)

        // Notify all pre-registered listeners of onCreate
        listeners.forEach { listener ->
            Logger.log("ActivityContextProvider: Notifying onCreate for listener: ${listener.javaClass.simpleName}")
            listener.onCreate(activity)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        val activity = activityRef.get()

        // Notify all pre-registered listeners of onResume
        listeners.forEach { it.onResume(activity) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        val activity = activityRef.get()

        // Notify all pre-registered listeners of onDestroy
        listeners.forEach { it.onDestroy(activity) }
        activityRef.set(null)
    }

    /**
     * Interface for lifecycle listeners.
     */
    interface LifecycleListener {
        fun onCreate(activity: ComponentActivity) {}
        fun onResume(activity: ComponentActivity?) {}
        fun onDestroy(activity: ComponentActivity?) {}
    }
}
