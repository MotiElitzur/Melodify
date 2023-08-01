package com.motiapps.melodify.presentation.splash

import androidx.lifecycle.viewModelScope
import com.motiapps.melodify.base.BaseViewModel
import com.motiapps.melodify.domain.model.User
import com.motiapps.melodify.domain.usecases.UserUseCases
import com.motiapps.melodify.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
//    private val dataViewModel: DataViewModel,
    private val userUseCase: UserUseCases
) : BaseViewModel() {

    private val _navigationEvent = MutableStateFlow<NavDirections?>(null)
    val navigationEvent: StateFlow<NavDirections?> = _navigationEvent

    init {
        viewModelScope.launch {
            userUseCase.insertUser(
                User(
                    id = "blabla",
                    name = "Alex",
                    creationTimestamp = 123
                )
            )


            val user = userUseCase.getUser("blabla")

            println("user blabla: $user")


            delay(5000)
            _navigationEvent.value = NavDirections.Home
        }
    }
}