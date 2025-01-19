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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import motiapps.melodify.R
import motiapps.melodify.common.language.domain.usecase.LanguageUseCases


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val languageUseCases: LanguageUseCases,
    private val permissionUseCases: PermissionsUseCases,
    private val showNotificationUseCase: ShowNotificationUseCase
) : ViewModel() {

    // Expose current language as a StateFlow
    private val _currentLanguage = MutableStateFlow("")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    init {
        fetchCurrentLanguage()
    }

    private fun fetchCurrentLanguage() {
        viewModelScope.launch {
            when (val result = languageUseCases.getLanguagesUseCase()) {
                is Resource.Success -> {
                    _currentLanguage.value = result.data
                }

                is Resource.Error -> {
                    Logger.d(
                        "HomeViewModel",
                        "Error fetching current language: ${result.exception.message}"
                    )
                }
            }
        }
    }

    fun changeLanguage(languageTag: String) {
        viewModelScope.launch {
            when (val result = languageUseCases.changeLanguageUseCase(languageTag)) {
                is Resource.Success -> {
                    _currentLanguage.value = languageTag
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error changing language: ${result.exception.message}")
                }
            }
        }
    }

    fun askForPermissions() {

        viewModelScope.launch {

            when (val result =
                permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    Logger.log("value = $value")
                }

                is Resource.Error -> {
                    val exception = result.exception
                    Logger.log("exception = $exception")
                }
            }

        }
    }

    fun showNotification() {
        viewModelScope.launch {
            showNotificationUseCase(
                Notification(
                    notificationDetails = NotificationDetails(
                        title = "Title",
                        message = "Message",
                        soundUri = null,
                    )
                )
            ).let {
                when (it) {
                    is Resource.Success -> {
                        val value = it.data
                        Logger.log("value = $value")
                    }

                    is Resource.Error -> {
                        val exception = it.exception
                        Logger.log("exception = $exception")
                    }
                }
            }
        }
    }
}