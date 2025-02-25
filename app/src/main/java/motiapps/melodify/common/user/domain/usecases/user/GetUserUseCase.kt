package motiapps.melodify.common.user.domain.usecases.user

import motiapps.melodify.common.user.data.model.User
import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.common.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): SuspendUseCase<String, Resource<User>>() {

        override suspend fun execute(params: String?): Resource<User> {
            return try {
                if (params == null) {
                    Resource.Error(Exception("User id is null"))
                } else {
                    userRepository.getUserById(params)
                }
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
}