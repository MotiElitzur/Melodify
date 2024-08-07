package motiapps.melodify.features.loading.domain.usecases

import motiapps.melodify.core.common.user.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.features.loading.domain.LoadingRepository
import motiapps.melodify.features.loading.domain.model.LoadingValues
import javax.inject.Inject

class LoadingUserUseCase @Inject constructor(
    private val loadingRepository: LoadingRepository
): SuspendUseCase<LoadingValues, Resource<User>>() {

    override suspend fun execute(params: LoadingValues?): Resource<User> {
        return try {
            if (params?.userId == null) {
                Resource.Error(Exception("User id is null"))
            } else {
                loadingRepository.loadUser(params.userId)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}