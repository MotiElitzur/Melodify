package motiapps.melodify.core.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class BaseSavedStateViewModel<State : ViewState, Event : IViewEvent?>(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // region State

    // Force the implementation of the initial state.
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    // Deciding which state management to use
    val uiState: StateFlow<State> = savedStateHandle.getStateFlow("state", initialState)

    // The current state to use outside the compose, that's require collectAsState.
    val state: State get() = uiState.value

    protected fun setState(reduce: State.() -> State) {
        savedStateHandle["state"] = state.reduce()
    }

    // endregion

    // region Event

    abstract fun triggerEvent(event: Event)

//    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
//    val uiEvent = _uiEvent.asSharedFlow()

    //    protected fun setEvent(event: Event) {
//        viewModelScope.launch { _uiEvent.emit(event) }
//    }


    // endregion


    //    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
//        viewModelScope.launch(
//            CoroutineExceptionHandler { _, throwable ->
//                Log.d(ERROR_TAG, throwable.message.orEmpty())
//            },
//            block = block
//        )



//
//    protected suspend fun <T> call(
//        callFlow: Flow<T>,
//        completionHandler: (collect: T) -> Unit = {}
//    ) {
//        callFlow
//            .catch { }
//            .collect {
//                completionHandler.invoke(it)
//            }
//    }
}