package motiapps.melodify.core.domain.usecases

import motiapps.melodify.core.domain.model.User
import motiapps.melodify.core.domain.repository.UserRepository

class InsertUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}