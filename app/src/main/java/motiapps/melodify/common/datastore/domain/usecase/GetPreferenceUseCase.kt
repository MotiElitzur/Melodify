package motiapps.melodify.common.datastore.domain.usecase

import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.repository.PreferencesRepository
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(
    private val repository: PreferencesRepository
) : SuspendUseCase<PreferenceObject<Any>, Resource<Any>>() {

    override suspend fun execute(params: PreferenceObject<Any>?): Resource<Any> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Key is null"))
            } else {
                repository.getPreference(key = params.key, defaultValue = params.value)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}