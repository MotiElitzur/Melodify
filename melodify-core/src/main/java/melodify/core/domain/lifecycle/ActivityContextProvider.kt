package melodify.core.domain.lifecycle

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import melodify.core.domain.Logger
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference

object ActivityContextProvider : DefaultLifecycleObserver {

    private val activityRef = AtomicReference<ComponentActivity?>()
    private val listeners = CopyOnWriteArrayList<LifecycleListener>()

    fun register(activity: ComponentActivity) {
        activity.lifecycle.addObserver(this)
        activityRef.set(activity)
    }

    fun unregister(activity: ComponentActivity) {
        if (activityRef.get() == activity) {
            activityRef.set(null)
        }
    }

    fun addListener(listener: LifecycleListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: LifecycleListener) {
        listeners.remove(listener)
    }

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
