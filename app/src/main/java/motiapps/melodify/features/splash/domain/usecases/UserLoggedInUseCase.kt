package motiapps.melodify.features.splash.domain.usecases

import motiapps.melodify.core.domain.base.UseCase
import motiapps.melodify.core.domain.repository.AuthRepository
import javax.inject.Inject

class UserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<Nothing?, Boolean>() {

    override suspend fun execute(params: Nothing?): Boolean {
        try {
            return authRepository.isUserAuthLoggedIn()
        } catch (e: Exception) {
            throw e
        }
    }
}