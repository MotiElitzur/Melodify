package motiapps.melodify.features.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import motiapps.melodify.common.Logger
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.language.domain.usecase.ChangeLanguageUseCase
import motiapps.melodify.common.language.domain.usecase.GetLanguageUseCase
import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.common.notifications.data.model.NotificationDetails
import motiapps.melodify.common.notifications.data.model.action.NotificationAction
import motiapps.melodify.common.notifications.domain.ShowNotificationUseCase
import motiapps.melodify.common.permissions.domain.usecase.PermissionsUseCases
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: PreferencesUseCases,
    private val permissionUseCases: PermissionsUseCases,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val showNotificationUseCase: ShowNotificationUseCase
) : ViewModel() {

    private val _currentLanguage = mutableStateOf("")
    val currentLanguage: State<String> = _currentLanguage

    init {
        fetchCurrentLanguage()
    }

    fun changeLanguage(languageTag: String) {
        viewModelScope.launch {
            when (val result = changeLanguageUseCase(languageTag)) {
                is Resource.Success -> {
                    _currentLanguage.value = languageTag
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error changing language: ${result.exception.message}")
                }
            }
        }
    }

    private fun fetchCurrentLanguage() {
        viewModelScope.launch {
            when (val result = getLanguageUseCase()) {
                is Resource.Success -> {
                    _currentLanguage.value = result.data
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error fetching current language: ${result.exception.message}")
                }
            }
        }


//            showNotificationUseCase(
//                Notification(
//                    notificationDetails = NotificationDetails(
//                        title = "Title",
//                        message = "Message",
//                        soundUri = null,
//                    ),
//                    notificationAction = listOf(
//                        NotificationAction.MarkAsRead(
//                            buttonNameId = "MarkAsRead",
//                            iconResId = 0,
//                            title = "Mark as read",
//                            isOpenActivity = false,
//                        )
//                    )
//                )
//            ).let {
//                when (it) {
//                    is Resource.Success -> {
//                        val value = it.data
//                        Logger.log("value = $value")
//                    }
//
//                    is Resource.Error -> {
//                        val exception = it.exception
//                        Logger.log("exception = $exception")
//                    }
//                }
//            }


//            pref.observePreferenceUseCase(PreferenceObject("key", "default value")).onEach {
//                when (it) {
//                    is Resource.Success -> {
//                        val value = it.data
//                        Logger.log("observe value = $value")
//                    }
//
//                    is Resource.Error -> {
//                        val exception = it.exception
//                        Logger.log("observe exception = $exception")
//                    }
//                }
//            }.launchIn(viewModelScope)
//
//
//            var isDarkMode = false

//            while (true) {
//                pref.setPreferenceUseCase(PreferenceObject("isDarkMode", isDarkMode)).let {
//                    when (it) {
//                        is Resource.Success -> {
//                            val value = it.data
//                            Logger.log("value = $value")
//                        }
//
//                        is Resource.Error -> {
//                            val exception = it.exception
//                            Logger.log("exception = $exception")
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
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//
//            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                is Resource.Success -> {
//                    val value = result.data
//                    Logger.log("value = $value")
//                }
//                is Resource.Error -> {
//                    val exception = result.exception
//                    Logger.log("exception = $exception")
//                }
//            }
//        }

}