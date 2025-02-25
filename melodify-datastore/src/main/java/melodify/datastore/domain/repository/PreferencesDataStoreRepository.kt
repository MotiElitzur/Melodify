package melodify.datastore.domain.repository

import kotlinx.coroutines.flow.Flow
import melodify.core.domain.Resource

interface PreferencesDataStoreRepository {
    suspend fun <T> setPreference(key: String, value: T, tableName: String): Resource<Unit>
    suspend fun <T> getPreference(key: String, defaultValueType: T, resultCanBeNull: Boolean, tableName: String): Resource<T>
    suspend fun removePreference(key: String, tableName: String): Resource<Unit>
    fun <T> observePreference(key: String, defaultValueType: T, resultCanBeNull: Boolean, tableName: String): Flow<Resource<T?>>
}