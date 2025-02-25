package melodify.datastore.domain.usecase

import melodify.core.domain.Resource
import melodify.core.domain.SuspendUseCase
import melodify.datastore.domain.repository.PreferencesDataStoreRepository
import melodify.datastore.domain.model.DataStoreItem
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) : SuspendUseCase<DataStoreItem<Any>, Resource<Any>>() {

    override suspend fun execute(params: DataStoreItem<Any>?): Resource<Any> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Key is null"))
            } else {
                repository.getPreference(key = params.key, defaultValueType = params.value, resultCanBeNull = params.isResultCanBeNull, tableName = params.tableName)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}