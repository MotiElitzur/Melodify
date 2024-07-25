package motiapps.melodify.features.splash.domain.usecases

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.SuspendUseCase
import motiapps.melodify.core.domain.base.UseCase
import motiapps.melodify.core.domain.repository.AuthRepository
import javax.inject.Inject

class UserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SuspendUseCase<Nothing?, Resource<Boolean>>() {

    override suspend fun execute(params: Nothing?): Resource<Boolean> {
        return authRepository.isUserAuthLoggedIn()
    }
}