package motiapps.melodify.common.firebase.user.domain.usecase.auth

import melodify.core.domain.Resource
import motiapps.melodify.common.firebase.user.domain.repository.AuthRepository
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
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