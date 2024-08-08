package motiapps.melodify.common.datastore.domain.usecase

import motiapps.melodify.common.datastore.domain.repository.PreferencesRepository
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class RemovePreferenceUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : SuspendUseCase<String, Resource<Unit>>() {

    override suspend fun execute(params: String?): Resource<Unit> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Key is null"))
            } else {
                preferencesRepository.removePreference(params)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}