package motiapps.melodify.common.notifications.domain.repository

import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.core.domain.base.Resource

interface NotificationRepository {
    fun showNotification(notification: Notification): Resource<Unit>
    fun cancelNotification(notificationId: Int): Resource<Unit>
}