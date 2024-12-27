package motiapps.melodify.features.register.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.common.Logger
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.register.domain.usecases.RegisterUseCases
import motiapps.melodify.features.register.data.model.CreateUserInput
import motiapps.melodify.features.register.data.model.RegisterMailInput
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<RegisterState, RegisterEvent>(savedStateHandle = savedStateHandle) {

    // region Override Methods

    override fun createInitialState(): RegisterState = RegisterState()

    override fun triggerEvent(event: RegisterEvent) {
        viewModelScope.launch {
            Logger.log("event: $event, state: $state")
            when (event) {
                is RegisterEvent.UpdateField -> updateField(event.field, event.value)
                is RegisterEvent.ValidateField -> validateField(event.field)
                is RegisterEvent.SubmitRegistration -> submitRegistration()
            }
        }
    }

    private suspend fun updateField(field: RegistrationField, value: String) {
        setState {
            state.copy(
                registrationFields = state.registrationFields.toMutableMap().apply {
                    this[field] = value
                },
                fieldErrors = state.fieldErrors.toMutableMap().apply {
                    remove(field)
                }
            )
        }
    }

    private suspend  fun validateField(field: RegistrationField) {
        val value = state.registrationFields[field] ?: ""
        val validationResult = when (field) {
            RegistrationField.FirstName, RegistrationField.LastName -> validateName(value)
            RegistrationField.Email -> validateEmail(value)
            RegistrationField.Password -> validatePassword(value)
            RegistrationField.ConfirmPassword -> validateConfirmPassword(value)
        }

        setState {
            state.copy(
                fieldErrors = state.fieldErrors.toMutableMap().apply {
                    if (validationResult.isFailure) {
                        this[field] = validationResult.exceptionOrNull()?.message ?: "Invalid input"
                    } else {
                        remove(field)
                    }
                }
            )
        }
    }

    private fun validateName(name: String): Result<Unit> {
        return if (name.length >= 2) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Name must be at least 2 characters long"))
        }
    }

    private fun validateEmail(email: String): Result<Unit> {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
        return if (email.matches(emailRegex)) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid email format"))
        }
    }

    private fun validatePassword(password: String): Result<Unit> {
        return if (password.length >= 8) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Password must be at least 8 characters long"))
        }
    }

    private fun validateConfirmPassword(confirmPassword: String): Result<Unit> {
        val password = state.registrationFields[RegistrationField.Password] ?: ""
        return if (confirmPassword == password) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Passwords do not match"))
        }
    }

    private suspend fun submitRegistration() {
        setState { state.copy(
            isLoading = true,
            generalError = null
        ) }

        val fields = state.registrationFields
        val allFieldsValid = RegistrationField.values().all { field ->
            validateField(field)
            !state.fieldErrors.containsKey(field)
        }

        if (allFieldsValid) {
            registerUser()
        } else {

            setState {
                state.copy(
                    isLoading = false,
                    generalError = "Please correct the errors in the form"
                )
            }
        }
    }


    private suspend fun registerUser() {
        val fields = state.registrationFields
        when (val result = registerUseCases.registerEmailPasswordUseCase(
            RegisterMailInput(
//                firstName = fields[RegistrationField.FirstName] ?: "",
//                lastName = fields[RegistrationField.LastName] ?: "",
                email = fields[RegistrationField.Email] ?: "",
                password = fields[RegistrationField.Password] ?: "",
//                confirmPassword = fields[RegistrationField.ConfirmPassword] ?: ""
            )
        )) {
            is Resource.Success -> {

                handleSuccessfulRegistration(result.data)
            }

            is Resource.Error -> {
                setState { state.copy(
                    isLoading = false,
                    generalError = result.errorType.getErrorMessage()
                ) }
            }
        }
    }


//    private suspend fun handleSetRegisterDetailsState(event: RegisterEvent.SetRegisterDetailsState) {
//        setState {
//            state.copy(
//                email = event.email,
//                password = event.password,
//                firstName = event.firstName,
//                lastName = event.lastName
//            )
//        }
//    }
//
//    private suspend fun handleStartRegister() {
//        if (state.email != null && state.password != null) {
//
//            setState { state.copy(isLoading = true) }
//
//            val registerResult = registerUseCases.registerEmailPasswordUseCase(
//                RegisterMailInput(email = state.email!!, password = state.password!!)
//            )
//
//            when (registerResult) {
//                is Resource.Success -> handleSuccessfulRegistration(registerResult.data)
//                is Resource.Error -> handleRegistrationError(registerResult)
//            }
//        } else {
//            setState { state.copy(
//                isLoading = false,
//                error = "Email or password is Empty"
//            ) }
//        }
//    }
//
    private suspend fun handleSuccessfulRegistration(firestoreUser: FirebaseUser) {
        val createUserResult = registerUseCases.createUserInDbUseCase(
            CreateUserInput(
                userId = firestoreUser.uid,
                firstName = state.registrationFields[RegistrationField.FirstName] ?: "",
                lastName = state.registrationFields[RegistrationField.LastName] ?: "",
            )
        )

        when (createUserResult) {
            is Resource.Success -> setState { state.copy(
                isLoading = false,
                navigateTo = NavDirections.Loading.route
            ) }
            is Resource.Error -> handleRegistrationError(createUserResult)
        }
    }

    private suspend fun handleRegistrationError(error: Resource.Error) {
        setState {
            state.copy(
                isLoading = false,
                generalError = error.errorType.getErrorMessage()
            )
        }
    }

    // endregion
}