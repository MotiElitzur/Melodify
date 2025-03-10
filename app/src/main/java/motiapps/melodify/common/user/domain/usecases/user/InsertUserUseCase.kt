package motiapps.melodify.common.user.domain.usecases.user

import motiapps.melodify.common.user.data.model.User
import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.common.user.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): SuspendUseCase<User, Resource<Unit>>() {

    override suspend fun execute(params: User?): Resource<Unit> {
        return try {
            if (params == null) {
                Resource.Error(Exception("User is null"))
            } else {
                userRepository.insertUser(params)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}