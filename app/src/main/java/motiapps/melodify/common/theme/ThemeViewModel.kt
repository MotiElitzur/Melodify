package motiapps.melodify.common.theme

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.core.domain.base.Resource
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
                println("ThemeViewModel.triggerEvent $event")
            }
        }
    }

    init {
        viewModelScope.launch {
            preferencesUseCases.observePreferenceUseCase(PreferenceObject(DARK_MODE_KEY, false, resultCanBeNull = true)).collect { darkModeStatus->
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
            PreferenceObject(
                key = DARK_MODE_KEY,
                value = isDarkMode
            )
        )
    }
}