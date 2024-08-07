package motiapps.melodify.core.common.datastore.domain.repository

import kotlinx.coroutines.flow.Flow
import motiapps.melodify.core.domain.base.Resource

interface PreferencesRepository {
    suspend fun <T> getPreference(key: String, defaultValue: T): Resource<T>
    suspend fun <T> setPreference(key: String, value: T): Resource<Unit>
    suspend fun removePreference(key: String): Resource<Unit>
    fun <T> observePreference(key: String, defaultValue: T): Flow<Resource<T>>
}