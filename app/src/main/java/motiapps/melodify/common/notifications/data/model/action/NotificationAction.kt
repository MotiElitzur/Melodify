package motiapps.melodify.common.notifications.data.model.action

import android.content.Context
import motiapps.melodify.R

sealed class NotificationAction {
    abstract val buttonNameId: String
    abstract val iconResId: Int
    abstract val title: String
    abstract val isOpenActivity: Boolean
    abstract val isDismissTheNotification: Boolean
    val actionType: NotificationActionType = NotificationActionType.BUTTON_PRESSED

    data class MarkAsRead(
        override val buttonNameId: String = "MarkAsRead",
        override val iconResId: Int = R.drawable.ic_notification,
        override val title: String = "Mark as read",
        override val isOpenActivity: Boolean = false,
        override val isDismissTheNotification: Boolean = true
    ) : NotificationAction()

    data class CustomAction (
        override val buttonNameId: String,
        override val iconResId: Int,
        override val title: String,
        override val isOpenActivity: Boolean = false,
        override val isDismissTheNotification: Boolean = true
    ) : NotificationAction()

    data class Unknown(
        override val buttonNameId: String = "Unknown",
        override val iconResId: Int = R.drawable.ic_notification,
        override val title: String = "Unknown",
        override val isOpenActivity: Boolean = false,
        override val isDismissTheNotification: Boolean = true
    ) : NotificationAction()


    companion object {
        private val subclasses = NotificationAction::class.sealedSubclasses

        fun fromName(name: String?): NotificationAction {
            return subclasses.firstOrNull { it.simpleName == name }?.objectInstance ?: Unknown()
        }

        fun fromNotificationId(notificationId: String): NotificationAction? {
            return subclasses
                .map { it.objectInstance ?: it.constructors.firstOrNull()?.callBy(emptyMap()) }
                .filterIsInstance<NotificationAction>()
                .firstOrNull { it.buttonNameId == notificationId }
        }
    }



}