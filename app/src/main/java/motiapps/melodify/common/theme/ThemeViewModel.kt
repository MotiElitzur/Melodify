package motiapps.melodify.common.theme

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import melodify.core.domain.Resource
import melodify.datastore.domain.model.DataStoreItem
import melodify.datastore.domain.usecase.PreferencesUseCases
import melodify.core.domain.Logger
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    savedStateHandle: SavedStateHandle
): BaseSavedStateViewModel<ThemeState, ThemeEvent>(savedStateHandle = savedStateHandle) {

    val DARK_MODE_KEY = "isDarkMode"

    override fun createInitialState(): ThemeState = ThemeState(
        isDarkTheme = null
    )

    override fun triggerEvent(event: ThemeEvent) {
        when (event) {
            is ThemeEvent.ThemeDarkModeChanged -> {
                Logger.log("ThemeViewModel.triggerEvent $event")
            }
        }
    }

    init {
        viewModelScope.launch {
            preferencesUseCases.observePreferenceUseCase(DataStoreItem(DARK_MODE_KEY, false, isResultCanBeNull = true)).collect { darkModeStatus->
                setState {
                    state.copy(
                        isDarkTheme = (darkModeStatus as? Resource.Success)?.data as? Boolean
                    )
                }
            }
        }
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        setState {
            state.copy(
                isDarkTheme = isDarkMode
            )
        }

        preferencesUseCases.setPreferenceUseCase(
            DataStoreItem(
                key = DARK_MODE_KEY,
                value = isDarkMode
            )
        )
    }
}