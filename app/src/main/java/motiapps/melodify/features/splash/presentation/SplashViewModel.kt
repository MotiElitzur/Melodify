package motiapps.melodify.features.splash.presentation

import androidx.lifecycle.viewModelScope
import motiapps.melodify.core.common.base.BaseViewModel
import motiapps.melodify.features.splash.domain.usecases.UserLoggedInUseCase
import motiapps.melodify.core.presentation.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userLoggedInUseCase: UserLoggedInUseCase,
) : BaseViewModel() {

    private val _initialRoute = MutableStateFlow<NavDirections?>(null)
    val initialRoute: StateFlow<NavDirections?> = _initialRoute

    init {
        viewModelScope.launch {
            val isUserLoggedIn = userLoggedInUseCase()
            println("SplashViewModel isUserLoggedIn: $isUserLoggedIn")
            _initialRoute.value = if (isUserLoggedIn) NavDirections.Loading else NavDirections.Login
        }
    }
}