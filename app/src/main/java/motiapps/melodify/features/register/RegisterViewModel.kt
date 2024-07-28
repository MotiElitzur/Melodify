package motiapps.melodify.features.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.register.data.model.CreateUserInput
import motiapps.melodify.features.register.data.model.RegisterMailInput
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<RegisterState, RegisterEvent>(savedStateHandle = savedStateHandle) {

    // region Override Methods

    override fun createInitialState(): RegisterState = RegisterState(
        isLoading = false
    )

    override fun triggerEvent(event: RegisterEvent) {
        viewModelScope.launch {
            println("event: $event, state: $state")
            when (event) {
                is RegisterEvent.SetRegisterDetailsState -> handleSetRegisterDetailsState(event)
                is RegisterEvent.SetStartRegister -> handleStartRegister()
                else -> { /* No action needed */ }
            }
        }
    }

    private suspend fun handleSetRegisterDetailsState(event: RegisterEvent.SetRegisterDetailsState) {
        setState {
            state.copy(
                email = event.email,
                password = event.password,
                firstName = event.firstName,
                lastName = event.lastName
            )
        }
    }

    private suspend fun handleStartRegister() {
        setState { state.copy(isLoading = true) }

        val registerResult = registerUseCases.registerEmailPasswordUseCase(
            RegisterMailInput(email = state.email, password = state.password)
        )

        when (registerResult) {
            is Resource.Success -> handleSuccessfulRegistration(registerResult.data)
            is Resource.Error -> handleRegistrationError(registerResult)
        }
    }

    private suspend fun handleSuccessfulRegistration(firestoreUser: FirebaseUser) {
        val createUserResult = registerUseCases.createUserInDbUseCase(
            CreateUserInput(
                userId = firestoreUser.uid,
                firstName = state.firstName,
                lastName = state.lastName
            )
        )

        when (createUserResult) {
            is Resource.Success -> setState { state.copy(
                isLoading = false,
                route = NavDirections.Loading.route
            ) }
            is Resource.Error -> handleRegistrationError(createUserResult)
        }
    }

    private suspend fun handleRegistrationError(error: Resource.Error) {
        setState {
            state.copy(
                isLoading = false,
                error = error.errorType.getErrorMessage()
            )
        }
    }

    // endregion
}