package com.motiapps.melodify.core.domain.usecases

import com.motiapps.melodify.core.domain.model.User
import com.motiapps.melodify.core.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
    ): User? {
        return repository.getUserById(userId)
    }
}