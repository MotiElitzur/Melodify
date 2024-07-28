package motiapps.melodify.features.register.domain

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.SuspendUseCase
import motiapps.melodify.core.domain.repository.RegisterRepository
import motiapps.melodify.features.register.data.model.CreateUserInput
import javax.inject.Inject

class CreateUserInDatabaseUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) : SuspendUseCase<CreateUserInput, Resource<Boolean>>() {

    override suspend fun execute(params: CreateUserInput?): Resource<Boolean> {

        if (params?.userId == null) {
            return Resource.Error(Exception("User id is null"))
        }

        return registerRepository.createUserInDatabase(params.userId, params.firstName, params.lastName)
    }

}