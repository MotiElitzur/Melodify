package motiapps.melodify.common.user.domain.usecases.auth

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.common.user.domain.repository.AuthRepository
import javax.inject.Inject

class UserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SuspendUseCase<Nothing?, Resource<Boolean>>() {

    override suspend fun execute(params: Nothing?): Resource<Boolean> {
        return authRepository.isUserAuthLoggedIn()
    }
}