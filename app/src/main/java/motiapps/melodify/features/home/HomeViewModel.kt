package motiapps.melodify.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.common.Logger
import motiapps.melodify.common.notifications.data.model.Notification
import motiapps.melodify.common.notifications.data.model.NotificationDetails
import motiapps.melodify.common.notifications.domain.ShowNotificationUseCase
import motiapps.melodify.common.permissions.domain.usecase.PermissionsUseCases
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.datastore.domain.usecase.SetPreferenceUseCase
import motiapps.melodify.common.language.domain.usecase.LanguageUseCases
import motiapps.melodify.common.user.domain.usecases.UserUseCases
import motiapps.melodify.common.user.domain.usecases.user.GetUserUseCase

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCases,
    private val languageUseCases: LanguageUseCases,
    private val permissionUseCases: PermissionsUseCases,
    private val preferencesUseCases: PreferencesUseCases,
    private val showNotificationUseCase: ShowNotificationUseCase,
) : ViewModel() {

    private val _currentLanguage = MutableStateFlow("")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        fetchCurrentLanguage()
        fetchUserName()
        fetchDarkModePreference()
    }

    private fun fetchCurrentLanguage() {
        viewModelScope.launch {
            when (val result = languageUseCases.getLanguagesUseCase()) {
                is Resource.Success -> {
                    _currentLanguage.value = result.data
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error fetching current language: ${result.exception.message}")
                }
            }
        }
    }

    private fun fetchUserName() {
        viewModelScope.launch {

            when (val result = userUseCase.getUserIdUseCase()) {
                is Resource.Success -> {
                    when (val userResult = userUseCase.getUserUseCase(result.data)) {
                        is Resource.Success -> {
                            _userName.value = userResult.data?.firstName
                        }
                        is Resource.Error -> {
                            Logger.d("HomeViewModel", "Error fetching user name: ${userResult.exception.message}")
                        }
                    }
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error fetching user ID: ${result.exception.message}")
                }
            }
        }
    }

    private fun fetchDarkModePreference() {
        viewModelScope.launch {
            when (val result = preferencesUseCases.getPreferenceUseCase(PreferenceObject("isDarkMode", false))) {
                is Resource.Success -> {
                    _isDarkMode.value = result.data as? Boolean ?: false
                }
                is Resource.Error -> {
                    Logger.d("HomeViewModel", "Error fetching dark mode preference: ${result.exception.message}")
                }
            }
        }
    }


    fun toggleDarkMode() {
        viewModelScope.launch {
            val newMode = !_isDarkMode.value
            _isDarkMode.value = newMode
            preferencesUseCases.setPreferenceUseCase(
                PreferenceObject(key = "isDarkMode", value = newMode)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun askForPermissions() {
        viewModelScope.launch {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionUseCases.requestPermissionUseCase(permission)
            } else {
                Resource.Success(true) // Automatically allow on older Android versions
            }
            when (result) {
                is Resource.Success -> Logger.log("Permission granted")
                is Resource.Error -> Logger.log("Permission denied: ${result.exception}")
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

    fun showNotification() {
        viewModelScope.launch {
            showNotificationUseCase(
                Notification(
                    notificationDetails = NotificationDetails(
                        title = "Test Notification",
                        message = "This is a test notification.",
                        soundUri = null
                    )
                )
            ).let {
                when (it) {
                    is Resource.Success -> Logger.log("Notification displayed")
                    is Resource.Error -> Logger.log("Failed to display notification: ${it.exception.message}")
                }
            }
        }
    }
}