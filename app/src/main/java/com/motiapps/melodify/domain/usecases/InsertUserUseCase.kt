package com.motiapps.melodify.domain.usecases

import com.motiapps.melodify.domain.model.User
import com.motiapps.melodify.domain.repository.UserRepository

class InsertUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}