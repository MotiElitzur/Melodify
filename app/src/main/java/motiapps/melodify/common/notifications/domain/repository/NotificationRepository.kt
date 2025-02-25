package motiapps.melodify.common.notifications.domain.repository

import motiapps.melodify.common.notifications.data.model.Notification
import melodify.core.domain.Resource

interface NotificationRepository {
    fun showNotification(notification: Notification): Resource<Unit>
    fun cancelNotification(notificationId: Int): Resource<Unit>
}