package motiapps.melodify.common.notifications.data.model

import android.graphics.Bitmap
import android.net.Uri

data class NotificationDetails(
    val title: String? = null,
    val message: String? = null,
    val priority: Int? = 1,
    val soundUri: Uri? = null,
    val largeIcon: Bitmap? = null
)