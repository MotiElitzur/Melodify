package motiapps.melodify.core.common.datastore.domain.usecase

import motiapps.melodify.core.common.datastore.data.model.PreferenceObject
import motiapps.melodify.core.common.datastore.domain.repository.PreferencesRepository
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class SetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : SuspendUseCase<PreferenceObject<Any>, Resource<Unit>>() {

    override suspend fun execute(params: PreferenceObject<Any>?): Resource<Unit> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Key is null"))
            } else {
                preferencesRepository.setPreference(params.key, params.value)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}