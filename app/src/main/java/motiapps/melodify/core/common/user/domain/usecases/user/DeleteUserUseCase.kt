package motiapps.melodify.core.common.user.domain.usecases.user

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.core.common.user.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): SuspendUseCase<String, Resource<Unit>>() {

        override suspend fun execute(params: String?): Resource<Unit> {
            return try {
                if (params == null) {
                    Resource.Error(Exception("User id is null"))
                } else {
                    userRepository.deleteUser(params)
                }
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
}