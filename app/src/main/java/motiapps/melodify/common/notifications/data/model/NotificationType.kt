package motiapps.melodify.common.notifications.data.model

sealed class NotificationType(
    val notificationId: Int = (System.currentTimeMillis() % (Integer.MAX_VALUE -1000)).toInt() + 1000, // Leave room 1 -1000 for system notifications
    var extraData: String? = null
) {

    data object Unknown: NotificationType()
    data object Test : NotificationType(notificationId = 999)


}