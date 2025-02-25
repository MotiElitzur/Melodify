package melodify.datastore.domain.usecase

import kotlinx.coroutines.flow.Flow
import melodify.datastore.domain.repository.PreferencesDataStoreRepository
import melodify.core.domain.Resource
import melodify.datastore.domain.model.DataStoreItem
import javax.inject.Inject

class ObservePreferenceUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
)  {

    operator fun invoke(params: DataStoreItem<Any>?): Flow<Resource<Any?>> {
        return repository.observePreference(key = params!!.key, defaultValueType = params.value, resultCanBeNull = params.isResultCanBeNull, tableName = params.tableName)
    }
}