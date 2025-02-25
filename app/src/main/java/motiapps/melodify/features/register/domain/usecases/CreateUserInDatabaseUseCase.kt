package motiapps.melodify.features.register.domain.usecases

import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.features.register.data.model.CreateUserInput
import motiapps.melodify.features.register.domain.repository.RegisterRepository
import javax.inject.Inject

class CreateUserInDatabaseUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) : SuspendUseCase<CreateUserInput, Resource<Unit>>() {

    override suspend fun execute(params: CreateUserInput?): Resource<Unit> {

        if (params?.userId == null) {
            return Resource.Error(Exception("User id is null"))
        }

        return registerRepository.createUserInDatabase(params.userId, params.firstName, params.lastName)
    }
}