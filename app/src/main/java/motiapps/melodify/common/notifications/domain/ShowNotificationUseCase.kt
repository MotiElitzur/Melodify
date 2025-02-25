package motiapps.melodify.common.notifications.domain

import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.common.notifications.domain.repository.NotificationRepository
import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class ShowNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
): SuspendUseCase<Notification, Resource<Unit>>() {

    override suspend fun execute(params: Notification?): Resource<Unit> {
        if (params == null) {
            return Resource.Error(Exception("NotificationDetails is null"))
        } else {
            try {
                notificationRepository.showNotification(params)
                return Resource.Success(Unit)
            } catch (e: Exception) {
                return Resource.Error(e)
            }
        }
    }
}