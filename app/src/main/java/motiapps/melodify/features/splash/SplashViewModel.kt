package motiapps.melodify.features.splash

import androidx.lifecycle.viewModelScope
import motiapps.melodify.core.common.base.BaseViewModel
import motiapps.melodify.core.domain.usecases.GetUserAuthUseCase
import motiapps.melodify.core.presentation.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserAuthUseCase: GetUserAuthUseCase,
) : BaseViewModel() {

    private val _initialRoute = MutableStateFlow<NavDirections?>(null)
    val initialRoute: StateFlow<NavDirections?> = _initialRoute

    init {
        viewModelScope.launch {
            val user = getUserAuthUseCase()
            println("user: $user")
            _initialRoute.value = if (user != null) NavDirections.Loading else NavDirections.Login
            println("SplashViewModel initialRoute: ${_initialRoute.value}")
        }
    }
}