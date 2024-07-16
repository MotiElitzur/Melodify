package motiapps.melodify.features.splash.domain.usecases

import motiapps.melodify.core.domain.repository.AuthRepository
import javax.inject.Inject

class UserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserAuthLoggedIn()
    }
}