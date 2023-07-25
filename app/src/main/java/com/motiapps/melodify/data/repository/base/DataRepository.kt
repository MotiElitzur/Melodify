package com.motiapps.melodify.data.repository.base

import com.motiapps.melodify.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DataRepository {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    suspend fun loadUserData() {
        delay(3000)  // Simulate a delay
//        _user.value = User("Alex")  // Set the user
    }
}