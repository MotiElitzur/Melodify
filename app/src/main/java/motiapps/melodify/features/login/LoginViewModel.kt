package motiapps.melodify.features.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.features.login.domain.usecase.LoginUseCases
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.base.error.LoginErrorType
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.login.domain.LoginEmailUseCaseInput
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    private val errorHandler: ErrorHandler,
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
                    println("Login Success")
                    setState {
                        state.copy(
                            isLoading = false,
                            route = NavDirections.Loading.route
                        )
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
                is LoginEvent.SetContinueAsGuestState -> {
                    setState {
                        state.copy(
                            isLoading = true,
                        )
                    }

                    triggerEvent(performLogin {
                        loginUseCases.loginAnonymousUseCase()
                    })


//                        when (val resource = loginUseCases.loginAnonymousUseCase()) {
//                            is Resource.Success -> {
//                                triggerEvent(LoginEvent.SetLoginSuccessState)
//                            }
//                            is Resource.Error -> {
//                                triggerEvent(LoginEvent.SetErrorState(resource.errorType.getErrorMessage()))
//                            }
//                        }

                }
                is LoginEvent.SetEmailAndPasswordState -> {

                    if (!validateEmail(event.email)) {
                        triggerEvent(LoginEvent.SetErrorState(LoginErrorType.InvalidEmail.getErrorMessage()))
                        return@launch
                    }

                    if (!validatePassword(event.password)) {
                        triggerEvent(LoginEvent.SetErrorState(LoginErrorType.InvalidPassword.getErrorMessage()))
                        return@launch
                    }

                    setState {
                        state.copy(
                            isLoading = true,
                        )
                    }

                    triggerEvent(performLogin {
                        loginUseCases.loginWithEmailUseCase(LoginEmailUseCaseInput(
                            email = event.email,
                            password = event.password
                        ))
                    })

//                    when (val resource = loginUseCases.loginWithEmailUseCase(LoginEmailUseCaseInput(
//                            email = event.email,
//                            password = event.password))) {
//                        is Resource.Success -> {
//                            triggerEvent(LoginEvent.SetLoginSuccessState)
//                        }
//                        is Resource.Error -> {
//                            println("Error: ${resource.exception} errorHandler: $errorHandler")
//                            triggerEvent(LoginEvent.SetErrorState(resource.errorType.getErrorMessage()))
//                        }
//                    }

                }
            }
        }
    }

    private suspend fun performLogin(loginAction: suspend () -> Resource<FirebaseUser>): LoginEvent {
        return when (val resource = loginAction()) {
            is Resource.Success -> {
                LoginEvent.SetLoginSuccessState
            }
            is Resource.Error -> {
                LoginEvent.SetErrorState(resource.errorType.getErrorMessage())
            }
        }
    }


    private fun validateEmail(email: String): Boolean {
        // Simple email validation (can be improved with regex)
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        // Password must be at least 6 characters long
        return password.length >= 6
    }

    // endregion
}