package motiapps.melodify.common.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import motiapps.melodify.common.datastore.domain.repository.PreferencesRepository
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override suspend fun <T> setPreference(key: String, value: T): Resource<Unit> {
        return try {
            dataStore.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is Double -> preferences[doublePreferencesKey(key)] = value
                    is Set<*> -> preferences[stringSetPreferencesKey(key)] = value as Set<String>
                    is ByteArray -> preferences[byteArrayPreferencesKey(key)] = value
                    else -> throw IllegalArgumentException("Unsupported type: ${value!!::class.java.name}")
                }
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun <T> getPreference(key: String, defaultValue: T): Resource<T> = try {
        val value = dataStore.data.map { it[getPreferenceKey(key, defaultValue)] ?: defaultValue }.first()
        Resource.Success(value as T)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override fun <T> observePreference(key: String, defaultValue: T): Flow<Resource<T>> = flow {
        try {
            val preferenceKey = getPreferenceKey(key, defaultValue)

            dataStore.data
                .map { preferences -> preferences[preferenceKey] as? T ?: defaultValue }
                .distinctUntilChanged()
                .collect { emit(Resource.Success(it)) }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    private fun <T> getPreferenceKey(key: String, value: T): Preferences.Key<*> = when (value) {
        is String -> stringPreferencesKey(key)
        is Int -> intPreferencesKey(key)
        is Boolean -> booleanPreferencesKey(key)
        is Float -> floatPreferencesKey(key)
        is Long -> longPreferencesKey(key)
        is Double -> doublePreferencesKey(key)
        is Set<*> -> stringSetPreferencesKey(key)
        is ByteArray -> byteArrayPreferencesKey(key)
        else -> throw IllegalArgumentException("Unsupported type: ${value!!::class.java.name}")
    }

    override suspend fun removePreference(key: String): Resource<Unit> = try {
        dataStore.edit { it.remove(getPreferenceKey<Any>(key, "")) }
        Resource.Success(Unit)
    } catch (e: Exception) {
        Resource.Error(e)
    }
}