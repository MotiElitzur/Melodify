package motiapps.melodify.features.splash.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import motiapps.melodify.core.presentation.base.BaseViewModel
import motiapps.melodify.features.splash.domain.usecases.UserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.navigation.NavDirections
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userLoggedInUseCase: UserLoggedInUseCase,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<SplashState, SplashEvent>(savedStateHandle = savedStateHandle) {

    // region init

    init {
        viewModelScope.launch {
            checkIfUserIsLoggedIn()
        }
    }

    // endregion

    // region override methods

    override fun createInitialState(): SplashState = SplashState (
        isLoading = true
    )

    override fun triggerEvent(event: SplashEvent) {
        viewModelScope.launch {
            when (event) {
                is SplashEvent.SetSplashState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            initialRoute = event.initialScreen.route
                        )
                    }
                }
            }
        }
    }

    // endregion

    // region private methods

    private suspend fun checkIfUserIsLoggedIn() {
        userLoggedInUseCase().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val isUserLoggedIn = resource.data
                    println("SplashViewModel isUserLoggedIn: $isUserLoggedIn")

                    val route: NavDirections = if (isUserLoggedIn) NavDirections.Loading else NavDirections.Login
                    triggerEvent(SplashEvent.SetSplashState(route))
                }
                is Resource.Error -> {
                    triggerEvent(SplashEvent.SetSplashState(NavDirections.Login))
                }
            }
        }
    }

    // endregion

}