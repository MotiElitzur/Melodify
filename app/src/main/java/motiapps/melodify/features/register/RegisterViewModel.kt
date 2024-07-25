package motiapps.melodify.features.register

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
class RegisterViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<RegisterState, RegisterEvent>(savedStateHandle = savedStateHandle) {

    // region Override Methods

    override fun createInitialState(): RegisterState = RegisterState(
        isLoading = false
    )

    override fun triggerEvent(event: RegisterEvent) {
        viewModelScope.launch {
//            when (event) {
//                is RegisterEvent.SetLoginSuccessState -> {
//                    println("Login Success")
//                    setState {
//                        state.copy(
//                            isLoading = false,
//                            route = NavDirections.Loading.route
//                        )
//                    }
//                }
//                is RegisterEvent.SetStartRegister -> {
//                    setState {
//                        state.copy(
//                            isLoading = false,
//                            route = NavDirections.Register.route
//                        )
//                    }
//                }
//                is RegisterEvent.SetErrorState -> {
//                    setState {
//                        state.copy(
//                            isLoading = false,
//                            error = event.error
//                        )
//                    }
//                }
//                is RegisterEvent.SetContinueAsGuestState -> {
//                    setState {
//                        state.copy(
//                            isLoading = true,
//                        )
//                    }
//
//                    triggerEvent(performLogin {
//                        loginUseCases.loginAnonymousUseCase()
//                    })
//                }
//                is RegisterEvent.SetEmailAndPasswordState -> {
//
//                    if (!validateEmail(event.email)) {
//                        triggerEvent(RegisterEvent.SetErrorState(LoginErrorType.InvalidEmail.getErrorMessage()))
//                        return@launch
//                    }
//
//                    if (!validatePassword(event.password)) {
//                        triggerEvent(RegisterEvent.SetErrorState(LoginErrorType.InvalidPassword.getErrorMessage()))
//                        return@launch
//                    }
//
//                    setState {
//                        state.copy(
//                            isLoading = true,
//                        )
//                    }
//
//                    triggerEvent(performLogin {
//                        loginUseCases.loginWithEmailUseCase(LoginEmailUseCaseInput(
//                            email = event.email,
//                            password = event.password
//                        ))
//                    })
//                }
//            }
        }
    }

    private suspend fun performLogin(loginAction: suspend () -> Resource<FirebaseUser>): RegisterEvent {
        return when (val resource = loginAction()) {
            is Resource.Success -> {
                RegisterEvent.SetLoginSuccessState
            }
            is Resource.Error -> {
                RegisterEvent.SetErrorState(resource.errorType.getErrorMessage())
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