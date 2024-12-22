package motiapps.melodify.common.notifications.domain.repository

import motiapps.melodify.common.notifications.data.model.Notification

interface NotificationRepository {
    fun showNotification(notification: Notification): Result<Unit>
    fun cancelNotification(notificationId: Int): Result<Unit>
}