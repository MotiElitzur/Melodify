package motiapps.melodify.common.notifications.data.model

import motiapps.melodify.common.notifications.data.model.action.NotificationAction
import motiapps.melodify.common.notifications.data.model.channel.NotificationChannel

data class Notification(
    val type: NotificationType = NotificationType.Unknown,
    val notificationDetails: NotificationDetails,
    val notificationChannel: NotificationChannel = NotificationChannel.Default,
    val notificationAction: List<NotificationAction>? = null
)