package motiapps.melodify.features.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.common.Logger
import motiapps.melodify.common.Utils
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.features.login.domain.usecase.LoginUseCases
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.login.domain.model.LoginCredentials
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<LoginState, LoginEvent>(savedStateHandle = savedStateHandle) {

    // region Override Methods
    override fun createInitialState(): LoginState = LoginState()

    override fun triggerEvent(event: LoginEvent) {
        Logger.log("LoginViewModel.triggerEvent $event , email: ${state.email} , password: ${state.password}")
        viewModelScope.launch {
            when (event) {
                is LoginEvent.EmailChanged -> updateEmail(event.email)
                is LoginEvent.PasswordChanged -> updatePassword(event.password)
                is LoginEvent.FieldFocusChanged -> updateFieldFocus(event.field, event.isFocused)
                is LoginEvent.LoginClicked -> login()
                is LoginEvent.ContinueAsGuestClicked -> continueAsGuest()
                is LoginEvent.RegisterClicked -> navigateToRegister()
                is LoginEvent.NavigationHandled -> clearNavigationEvent()
            }
        }
    }

    private suspend fun updateEmail(email: String) {
        setState {
            state.copy(
                email = email,
                emailError = null
            )
        }

        updateLoginButtonState()
    }

    private suspend fun updatePassword(password: String) {
       setState {
            state.copy(
                password = password,
                passwordError = null,
            )
        }
        updateLoginButtonState()
    }

    private suspend fun updateFieldFocus(field: LoginField, isFocused: Boolean) {
        if (!isFocused) {
            setState {
                when (field) {
                    LoginField.EMAIL -> state.copy(
                        emailTouched = true,
                        emailError = getEmailError(state.email)
                    )
                    LoginField.PASSWORD -> state.copy(
                        passwordTouched = true,
                        passwordError = getPasswordError(state.password)
                    )
                }
            }
            updateLoginButtonState()
        }
    }

    private fun getEmailError(email: String): String? =

        if (email.isEmpty() || Utils.isEmailValid(email)) null else "Invalid email format"

    private fun getPasswordError(password: String): String? =
        if (password.isEmpty() ||  Utils.isPasswordValid(password)) null else "Invalid password"

    private suspend fun updateLoginButtonState() {
        val currentState = uiState.value
        val isLoginEnabled = Utils.isEmailValid(currentState.email) &&
                Utils.isPasswordValid(currentState.password)

        setState { state.copy(isLoginEnabled = isLoginEnabled) }
    }

    private suspend fun login() {
        val email = state.email
        val password = state.password

        if (!Utils.isEmailValid(email)) {
            setState { state.copy(error = "Invalid email format") }
            return
        }

        if (!Utils.isPasswordValid(password)) {
            setState { state.copy(error = "Invalid password") }
            return
        }

        performLogin { loginUseCases.loginWithEmailUseCase(LoginCredentials(email, password)) }
    }

    private suspend fun continueAsGuest() {
        performLogin { loginUseCases.loginAnonymousUseCase() }
    }

    private suspend fun navigateToRegister() {
        setState { state.copy(navigationRoute = NavDirections.Register.route) }
    }

    private suspend fun navigateToLoading() {
        setState { state.copy(isLoading = false, navigationRoute = NavDirections.Loading.route) }
    }

    private suspend fun clearNavigationEvent() {
        setState { state.copy(isLoading = false, navigationRoute = null) }
    }

    private suspend fun performLogin(loginAction: suspend () -> Resource<Any>) {
        setState { state.copy(isLoading = true, error = null) }
        when (val resource = loginAction()) {
            is Resource.Success -> navigateToLoading()
            is Resource.Error -> setState { state.copy(isLoading = false, error = resource.errorType.getErrorMessage()) }
        }
    }

    // endregion
}