package motiapps.melodify.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.common.notifications.data.model.NotificationDetails
import motiapps.melodify.common.notifications.data.model.action.NotificationAction
import motiapps.melodify.common.notifications.domain.ShowNotificationUseCase
import motiapps.melodify.common.permissions.domain.usecase.PermissionsUseCases
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: PreferencesUseCases,
    private val permissionUseCases: PermissionsUseCases,
    private val showNotificationUseCase: ShowNotificationUseCase
) : ViewModel() {

    init {

        pref.observePreferenceUseCase(PreferenceObject("key", "default value")).onEach {
            when (it) {
                is Resource.Success -> {
                    val value = it.data
                    println("observe value = $value")
                }

                is Resource.Error -> {
                    val exception = it.exception
                    println("observe exception = $exception")
                }
            }
        }.launchIn(viewModelScope)


        var isDarkMode = false

        viewModelScope.launch {

            showNotificationUseCase(
                Notification(
                    notificationDetails = NotificationDetails(
                        title = "Title",
                        message = "Message",
                        soundUri = null,
                    ),
                    notificationAction = listOf(
                        NotificationAction.MarkAsRead(
                            buttonNameId = "MarkAsRead",
                            iconResId = 0,
                            title = "Mark as read",
                            isOpenActivity = false,
                        )
                    )
                )
            ).let {
                when (it) {
                    is Resource.Success -> {
                        val value = it.data
                        println("value = $value")
                    }

                    is Resource.Error -> {
                        val exception = it.exception
                        println("exception = $exception")
                    }
                }
            }


//            while (true) {
//                pref.setPreferenceUseCase(PreferenceObject("isDarkMode", isDarkMode)).let {
//                    when (it) {
//                        is Resource.Success -> {
//                            val value = it.data
//                            println("value = $value")
//                        }
//
//                        is Resource.Error -> {
//                            val exception = it.exception
//                            println("exception = $exception")
//                        }
//                    }
//                }
//
//                isDarkMode = !isDarkMode
//                kotlinx.coroutines.delay(1000)
//            }

        }

//        viewModelScope.launch {
//
//
//
//            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    println("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    println("exception = $exception")
//                }
//            }
//        }
    }
}