package motiapps.melodify.core.domain.usecases.user

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.core.domain.repository.UserRepository
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