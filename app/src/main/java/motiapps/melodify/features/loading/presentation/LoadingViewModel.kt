package motiapps.melodify.features.loading.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.common.user.domain.model.UserPartialUpdate
import motiapps.melodify.common.user.domain.usecases.UserUseCases
import motiapps.melodify.core.presentation.base.BaseSavedStateViewModel
import motiapps.melodify.core.presentation.base.error.ErrorHandler.Companion.getErrorMessage
import motiapps.melodify.core.presentation.navigation.NavDirections
import motiapps.melodify.features.loading.domain.model.LoadingValues
import motiapps.melodify.features.loading.domain.usecases.LoadingUserUseCase
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingUserUseCase: LoadingUserUseCase,
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : BaseSavedStateViewModel<LoadingState, LoadingEvent>(savedStateHandle = savedStateHandle) {

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        when (val userIdResult = userUseCases.getUserIdUseCase()) {
            is Resource.Success -> handleUserIdSuccess(userIdResult.data)
            is Resource.Error -> handleError(userIdResult.errorType.getErrorMessage())
        }
    }

    private suspend fun handleUserIdSuccess(userId: String) {
        when (val userLoadingResult = loadingUserUseCase(LoadingValues(userId = userId))) {
            is Resource.Success -> handleLoadingUserSuccess(userId)
            is Resource.Error -> handleError(userLoadingResult.errorType.getErrorMessage())
        }
    }

    private suspend fun handleLoadingUserSuccess(userId: String) {
        when (val userUpdateResult = userUseCases.updateUserUseCase(
            UserPartialUpdate(
                userId = userId,
                fields = mapOf("lastActive" to Timestamp.now())
            )
        )) {
            is Resource.Success -> handleUpdateUserSuccess()
            is Resource.Error -> handleError(userUpdateResult.errorType.getErrorMessage())
        }
    }

    private suspend fun handleUpdateUserSuccess() {
        setState { state.copy(isLoading = false, navigateTo = NavDirections.Home.route) }
    }

    private suspend fun handleError(errorMessage: String) {
        setState { state.copy(isLoading = false, error = errorMessage) }
    }

    override fun createInitialState(): LoadingState = LoadingState()

    override fun triggerEvent(event: LoadingEvent) {
        println("event: $event, state: $state")
    }
}