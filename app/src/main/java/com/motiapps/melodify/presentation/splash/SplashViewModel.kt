package com.motiapps.melodify.presentation.splash

import androidx.lifecycle.viewModelScope
import com.motiapps.melodify.base.BaseViewModel
import com.motiapps.melodify.domain.model.User
import com.motiapps.melodify.domain.usecases.UserUseCases
import com.motiapps.melodify.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class SplashViewModel @Inject constructor(
//    private val dataViewModel: DataViewModel,
    private val userUseCase: UserUseCases
) : BaseViewModel() {

    private val _navigationEvent = MutableStateFlow<NavDirections?>(null)
    val navigationEvent: StateFlow<NavDirections?> = _navigationEvent

    init {
        viewModelScope.launch {

            val timeMillis = measureTimeMillis {

//                val insertJob = async(Dispatchers.IO) {
//                    userUseCase.insertUser(
//                        User(
//                            id = "blabla",
//                            name = "Alex",
//                            creationTimestamp = 123
//                        )
//                    )
//                }
//
//                val getUserJob = async(Dispatchers.IO) {
//                    userUseCase.getUser("blabla")
//                }
//
//                awaitAll(insertJob, getUserJob)
//                println("user blabla: ${getUserJob.getCompleted()}")

//                userUseCase.insertUser(
//                    User(
//                        id = "blabla",
//                        name = "Alex",
//                        creationTimestamp = 123
//                    )
//                )
//                val user = userUseCase.getUser("blabla")
//
//                println("user blabla: $user")

                userUseCase.insertUser(
                    User(
                        id = "blabla",
                        name = "Alex",
                        creationTimestamp = 123
                    )
                )

                userUseCase.insertUser(
                    User(
                        id = "blabla3",
                        name = "Alex",
                        creationTimestamp = 1236
                    )
                )
            }

            println("blabla timeMillis: $timeMillis")

            val timeMillis2 = measureTimeMillis {


                val insertJob = async(Dispatchers.IO) {
                    userUseCase.insertUser(
                        User(
                            id = "blabla",
                            name = "Alex",
                            creationTimestamp = 123
                        )
                    )
                }

                val insertJob2 = async(Dispatchers.IO) {
                    userUseCase.insertUser(
                        User(
                            id = "blabla4",
                            name = "Alex4",
                            creationTimestamp = 1234
                        )
                    )
                }

                awaitAll(insertJob, insertJob2)
            }

            println("blabla timeMillis2: $timeMillis2")

            _navigationEvent.value = NavDirections.Home
        }
    }
}