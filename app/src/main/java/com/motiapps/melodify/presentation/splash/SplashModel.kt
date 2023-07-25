package com.motiapps.melodify.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motiapps.melodify.navigation.NavDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashModel : ViewModel() {

    private val _navigationEvent = MutableStateFlow<NavDirections?>(null)
    val navigationEvent: StateFlow<NavDirections?> = _navigationEvent

    init {
        viewModelScope.launch {
            delay(2000)  // simulate some initial loading or processing

            // Determine navigation destination here
            _navigationEvent.value = NavDirections.Home
        }
    }
}