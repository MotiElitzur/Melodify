package motiapps.melodify.core.domain.usecases.user

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.core.domain.model.UserDto
import motiapps.melodify.core.domain.repository.UserRepository
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