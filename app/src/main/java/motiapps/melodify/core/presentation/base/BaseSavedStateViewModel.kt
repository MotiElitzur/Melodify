package motiapps.melodify.core.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseSavedStateViewModel<State : IViewState, Event : IViewEvent?> (
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // region State

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    val uiState: StateFlow<State> = savedStateHandle.getStateFlow("state", initialState)

    val state: State get() = uiState.value

    private val stateMutex = Mutex()

    protected suspend fun setState(reduce: suspend (State) -> State) {
        // Avoid Race Condition and use always the latest state.
        stateMutex.withLock {
            savedStateHandle["state"] = reduce(state)
        }
    }

    // endregion

    // region Event

    abstract fun triggerEvent(event: Event)

    // endregion
}