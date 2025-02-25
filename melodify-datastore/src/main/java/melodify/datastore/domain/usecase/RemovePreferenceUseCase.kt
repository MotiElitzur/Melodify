package melodify.datastore.domain.usecase

import melodify.core.domain.SuspendUseCase
import melodify.datastore.domain.repository.PreferencesDataStoreRepository
import melodify.core.domain.Resource
import melodify.datastore.domain.model.DataStoreItem
import javax.inject.Inject

class RemovePreferenceUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) : SuspendUseCase<DataStoreItem<Any>, Resource<Unit>>() {

    override suspend fun execute(params: DataStoreItem<Any>?): Resource<Unit> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Key is null"))
            } else {
                preferencesDataStoreRepository.removePreference(params.key, params.tableName)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}