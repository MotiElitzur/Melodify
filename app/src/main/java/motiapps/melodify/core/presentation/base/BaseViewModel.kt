package motiapps.melodify.core.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<State : IViewState, Event : IViewEvent?> : ViewModel() {

    // region State

    // Force the implementation of the initial state.
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState

    // The current state to use outside the compose, that's require collectAsState.
    val state: State get() = uiState.value

    protected fun setState(reduce: State.() -> State) {
        // Use the update method to avoid race conditions.
        _uiState.update { currentState -> currentState.reduce() }
        println("$state")
    }

    // endregion

    // region Event

    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    abstract fun triggerEvent(event: Event)

    // endregion


    //    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
//        viewModelScope.launch(
//            CoroutineExceptionHandler { _, throwable ->
//                Log.d(ERROR_TAG, throwable.message.orEmpty())
//            },
//            block = block
//        )


//    protected fun setEvent(event: Event) {
//        viewModelScope.launch { _uiEvent.emit(event) }
//    }
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