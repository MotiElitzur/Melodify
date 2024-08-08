package motiapps.melodify.common.datastore.domain.usecase

import kotlinx.coroutines.flow.Flow
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.repository.PreferencesRepository
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject

class ObservePreferenceUseCase @Inject constructor(
    private val repository: PreferencesRepository
)  {

    operator fun invoke(params: PreferenceObject<Any>?): Flow<Resource<Any>> {
        return repository.observePreference(key = params!!.key, defaultValue = params.value)
    }
}