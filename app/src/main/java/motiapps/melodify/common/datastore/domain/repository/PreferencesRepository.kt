package motiapps.melodify.common.datastore.domain.repository

import kotlinx.coroutines.flow.Flow
import motiapps.melodify.core.domain.base.Resource

interface PreferencesRepository {
    suspend fun <T> getPreference(key: String, defaultValueType: T, resultCanBeNull: Boolean): Resource<T>
    suspend fun <T> setPreference(key: String, value: T): Resource<Unit>
    suspend fun removePreference(key: String): Resource<Unit>
    fun <T> observePreference(key: String, defaultValueType: T, resultCanBeNull: Boolean): Flow<Resource<T?>>
}