package motiapps.melodify.common.user.domain.usecases.auth

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.common.user.domain.repository.AuthRepository
import javax.inject.Inject

class UserAuthIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SuspendUseCase<Nothing?, Resource<String>>() {

    override suspend fun execute(params: Nothing?): Resource<String> {
        return try {
            val userId = authRepository.getAuthUserId()
            if (userId == null) {
                Resource.Error(Exception("User id is null"))
            } else {
                Resource.Success(userId)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}