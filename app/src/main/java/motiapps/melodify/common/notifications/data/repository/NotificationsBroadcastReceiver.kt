package motiapps.melodify.common.notifications.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import motiapps.melodify.common.Logger
import motiapps.melodify.common.notifications.data.model.NotificationType
import motiapps.melodify.common.notifications.data.model.action.NotificationAction

@AndroidEntryPoint
class NotificationsBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.log("NotificationsBroadcastReceiver.onReceive(). action=${intent?.action} extras=${intent?.extras?.keySet()?.map { it to intent.extras?.get(it) }}")

        val actionTypesList: List<String> = NotificationType::class.sealedSubclasses.map { it.simpleName ?: "" }
        val notificationTypeName: String? = intent?.extras?.keySet()?.find { actionTypesList.contains(it) }
        val actionTypeName: String? = intent?.extras?.getString(notificationTypeName)
        val buttonNameId: String? = intent?.extras?.getString(actionTypeName)

        if (buttonNameId != null) {

            val notificationAction: NotificationAction = NotificationAction.fromName(buttonNameId)

            Logger.log("notificationAction: $notificationAction")

            Logger.log("NotificationsBroadcastReceiver onReceive notification type: $notificationTypeName, action type: $actionTypeName, button name id: $buttonNameId, extras: ${intent?.extras?.keySet()?.map { it to (intent?.extras?.getString(it) ?: "null")}}")
        } else {
            Logger.log("NotificationsBroadcastReceiver onReceive notification type: $notificationTypeName, action type: $actionTypeName, button name id: $buttonNameId, notification action: null, extras: ${intent?.extras?.keySet()?.map { it to (intent?.extras?.getString(it) ?: "null")}}")
        }

        Logger.log("NotificationsBroadcastReceiver onReceive notification type: $notificationTypeName, action type: $actionTypeName, button name id: $buttonNameId")
    }
}