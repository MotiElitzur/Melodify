package motiapps.melodify.features.loading.domain.usecases

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.features.loading.domain.LoadingRepository
import javax.inject.Inject

class LoadingUserUseCase @Inject constructor(
    private val loadingRepository: LoadingRepository
): SuspendUseCase<String, Resource<User>>() {

    override suspend fun execute(params: String?): Resource<User> {
        return try {
            if (params == null) {
                Resource.Error(Exception("User id is null"))
            } else {
                loadingRepository.loadUser(params)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}