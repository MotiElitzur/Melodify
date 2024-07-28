package motiapps.melodify.features.login.presentaion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.core.common.Utils
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.features.login.domain.usecase.LoginUseCases
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.base.error.LoginErrorType
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.login.LoginData
import motiapps.melodify.features.login.domain.LoginEmailUseCaseInput
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<LoginState, LoginEvent>(savedStateHandle = savedStateHandle) {


    // region Override Methods
    override fun createInitialState(): LoginState = LoginState(
        isLoading = false
    )

    override fun triggerEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.SetLoginSuccessState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            route = NavDirections.Loading.route
                        )
                    }
                }
                is LoginEvent.SetStartRegister -> {
                    setState {
                        state.copy(route = NavDirections.Register.route)
                    }
                }
                is LoginEvent.SetErrorState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            error = event.error
                        )
                    }
                }
                is LoginEvent.SaveToNextState -> {
                    setState {
                        state.copy(
                            route = null
                        )
                    }
                }
                is LoginEvent.SetContinueAsGuestState -> {
                    setState { state.copy(isLoading = true) }
                    performLogin { loginUseCases.loginAnonymousUseCase() }
                }
                is LoginEvent.SetLoginDataState -> {

                    val loginData: LoginData = event.loginData

                    setState {
                        state.copy(
                            email = loginData.email,
                            password = loginData.password
                        )
                    }

                    if (!Utils.isEmailValid(loginData.email)) {
                        triggerEvent(LoginEvent.SetErrorState(LoginErrorType.InvalidEmail.getErrorMessage()))
                        return@launch
                    }
                    if (!Utils.isPasswordValid(loginData.password)) {
                        triggerEvent(LoginEvent.SetErrorState(LoginErrorType.InvalidPassword.getErrorMessage()))
                        return@launch
                    }
                    setState { state.copy(isLoading = true) }
                    performLogin {
                        loginUseCases.loginWithEmailUseCase(LoginEmailUseCaseInput(
                            email = loginData.email,
                            password = loginData.password
                        ))
                    }
                }
            }
        }
    }

    private suspend fun performLogin(loginAction: suspend () -> Resource<FirebaseUser>) {
        when (val resource = loginAction()) {
            is Resource.Success -> triggerEvent(LoginEvent.SetLoginSuccessState)
            is Resource.Error -> triggerEvent(LoginEvent.SetErrorState(resource.errorType.getErrorMessage()))
        }
    }

    // endregion
}