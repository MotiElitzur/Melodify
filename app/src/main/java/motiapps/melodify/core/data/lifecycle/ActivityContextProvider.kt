package motiapps.melodify.core.data.lifecycle

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.Logger
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@ActivityRetainedScoped
class ActivityContextProvider @Inject constructor(
    private val listeners: List<LifecycleListener>, // Injected as a dependency
) : DefaultLifecycleObserver {

    private val activityRef = AtomicReference<ComponentActivity>()

    val activity: ComponentActivity
        get() = activityRef.get() ?: throw IllegalStateException("Activity is not available")

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
