package com.motiapps.melodify.core.domain.usecases

import com.motiapps.melodify.core.domain.model.User
import com.motiapps.melodify.core.domain.repository.UserRepository

class InsertUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}