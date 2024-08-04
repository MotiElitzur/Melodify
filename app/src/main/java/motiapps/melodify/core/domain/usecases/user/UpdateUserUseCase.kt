package motiapps.melodify.core.domain.usecases.user

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.core.domain.model.UserPartialUpdate
import motiapps.melodify.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): SuspendUseCase<UserPartialUpdate, Resource<Unit>>() {

    override suspend fun execute(params: UserPartialUpdate?): Resource<Unit> {
        return try {
            if (params?.userId == null) {
                Resource.Error(Exception("User is null"))
            } else {
                userRepository.updateUser(params.userId, params.fields)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}