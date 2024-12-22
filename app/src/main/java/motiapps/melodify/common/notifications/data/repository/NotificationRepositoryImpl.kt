package motiapps.melodify.common.notifications.data.repository

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import motiapps.melodify.R
import motiapps.melodify.common.notifications.data.model.channel.NotificationChannel
import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.common.notifications.data.model.NotificationType
import motiapps.melodify.common.notifications.data.model.action.NotificationActionType
import motiapps.melodify.common.notifications.domain.repository.NotificationRepository
import motiapps.melodify.core.presentation.MainActivity
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val context: Context,
) : NotificationRepository {

    // region NotificationRepository

    @SuppressLint("MissingPermission")
    override fun showNotification(notification: Notification): Result<Unit> {
        return try {
            createNotificationChannel(notification.notificationChannel)
            val notificationBuilder = createNotificationBuilder(notification)

            with(NotificationManagerCompat.from(context)) {
                notify(notification.type.notificationId, notificationBuilder.build())
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun cancelNotification(notificationId: Int): Result<Unit> {
        return try {
            NotificationManagerCompat.from(context).cancel(notificationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // endregion

    private fun createNotificationBuilder(notification: Notification): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, notification.notificationChannel.id).apply {
            setSmallIcon(R.drawable.ic_notification)

            // Set an intent to open activity when notification pressed.
            setContentIntent(getActivityIntent(notification.type, NotificationActionType.CLICK))

            // Set On Notification Dismissed Broadcast.
            setDeleteIntent(getBroadcastIntent(notification.type, NotificationActionType.DISMISSED))

            notification.notificationDetails.let { details ->
                details.title?.let { setContentTitle(it) }
                details.message?.let { setContentText(it) }
                details.largeIcon?.let { setLargeIcon(it) }
                details.priority?.let { setPriority(it) }
                details.soundUri?.let { setSound(it) }
            }

            notification.notificationAction?.forEach { action ->
                val actionIntent: PendingIntent = if (action.isOpenActivity) {
                    getActivityIntent(
                        notificationType = notification.type,
                        actionType = action.actionType,
                        buttonName = action.buttonNameId
                    )
                } else {
                    getBroadcastIntent(
                        notificationType = notification.type,
                        actionType = action.actionType,
                        buttonName = action.buttonNameId,
                    )
                }

                addAction(action.iconResId, action.title, actionIntent)
            }
        }
    }

    private fun createNotificationChannel(channel: NotificationChannel) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Avoid creating the same channel.
        if (notificationManager.getNotificationChannel(channel.id) == null) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val notificationChannel = NotificationChannel(
                channel.id,
                channel.name,
                channel.importance
            ).apply {
                description = channel.description
                enableVibration(channel.isUsingSound)
                channel.soundUri?.let { setSound(it, audioAttributes) }
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * Create an activity intent to open when notification pressed.
     */
    private fun getActivityIntent(
        notificationType: NotificationType,
        actionType: NotificationActionType,
        buttonName: String? = null,
        activityClass: Class<*>? = MainActivity::class.java): PendingIntent {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, activityClass).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(actionType.name, notificationType::class.simpleName)

            if (notificationType.extraData != null) {
                putExtra("ExtraData", notificationType.extraData)
            }

            // Add button name to intent if button pressed.
            if (actionType == NotificationActionType.BUTTON_PRESSED) {
                putExtra(notificationType::class.simpleName, buttonName)
            }
        }

        // Create pending intent, we need random id to avoid override the same pending intent.
        return PendingIntent.getActivity(context, (System.currentTimeMillis() % Integer.MAX_VALUE).toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
    }

    /**
     * Create an broadcast intent to open when notification pressed.
     */
    private fun getBroadcastIntent(
        notificationType: NotificationType,
        actionType: NotificationActionType,
        buttonName: String? = null,
        broadcastClass: Class<*>? = NotificationsBroadcastReceiver::class.java): PendingIntent {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, broadcastClass).apply {

            // Add notification type, we enter the data not like receiver because we want to catch the action first.
            putExtra(notificationType::class.simpleName, actionType.name)
            println("NotificationRepositoryImpl.getBroadcastIntent() notificationType::class.simpleName=${notificationType::class.simpleName} actionType.name=${actionType.name}")

            // Add button pressed name if exist.
            if (buttonName != null) {
                putExtra(actionType.name, buttonName)
                println("NotificationRepositoryImpl.getBroadcastIntent() actionType.name=${actionType.name} buttonName=$buttonName")
            }
        }

        // Create pending intent, we need random id to avoid override the same pending intent.
        return PendingIntent.getBroadcast(context, (System.currentTimeMillis() % Integer.MAX_VALUE).toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
    }
}