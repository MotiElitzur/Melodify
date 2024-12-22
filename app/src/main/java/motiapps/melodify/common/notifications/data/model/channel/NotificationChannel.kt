package motiapps.melodify.common.notifications.data.model.channel

import android.app.NotificationManager
import android.net.Uri

sealed class NotificationChannel {
    abstract val id: String
    abstract val name: String
    abstract val description: String
    abstract val importance: Int
    abstract val soundUri: Uri?
    abstract val isUsingSound: Boolean

    data object Default : NotificationChannel() {
        override val id = "default_channel"
        override val name = "Default Channel"
        override val description = "Default channel for notifications"
        override val importance = NotificationManager.IMPORTANCE_DEFAULT
        override val soundUri: Uri? = null
        override val isUsingSound = false
    }

    data object DefaultWithSound : NotificationChannel() {
        override val id = "default_channel_with_sound"
        override val name = "Default Channel with Sound"
        override val description = "Default channel for notifications with sound"
        override val importance = NotificationManager.IMPORTANCE_DEFAULT
        override val soundUri: Uri? = null
        override val isUsingSound = true
    }

    data class Custom(
        override val id: String,
        override val name: String,
        override val description: String,
        override val importance: Int,
        override val soundUri: Uri?,
        override val isUsingSound: Boolean
    ) : NotificationChannel()
}
