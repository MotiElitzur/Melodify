package com.motiapps.melodify.domain.usecases

import com.motiapps.melodify.domain.model.User
import com.motiapps.melodify.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
    ): User? {
        return repository.getUserById(userId)
    }
}