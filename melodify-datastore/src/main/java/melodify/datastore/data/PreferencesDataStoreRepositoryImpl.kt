package melodify.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import melodify.core.domain.Resource
import melodify.datastore.domain.repository.PreferencesDataStoreRepository
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataStoreRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val scope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob())
) : PreferencesDataStoreRepository {

    @Volatile
    private var config = DataStoreConfig.getConfig()
    private val cacheMap = ConcurrentHashMap<String, ConcurrentHashMap<String, Any>>()
    private val dataStores = ConcurrentHashMap<String, DataStore<Preferences>>()

    private fun getDataStore(tableName: String): DataStore<Preferences> {
        return dataStores.getOrPut(tableName) {
            PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile("datastore_$tableName") }
            )
        }
    }

    sealed class DataStoreException : Exception() {
        data class StorageError(override val cause: Throwable) : DataStoreException()
        data class EncryptionError(override val cause: Throwable) : DataStoreException()
        data class InvalidTypeError(val expectedType: String) : DataStoreException()
    }

    override suspend fun <T> setPreference(key: String, value: T, tableName: String): Resource<Unit> = withContext(dispatcher) {
        try {
            val dataStore = getDataStore(tableName)
            dataStore.edit { preferences ->
                val encryptedValue = when (value) {
                    is String -> Crypto.encrypt(value.toByteArray())
                    is Int -> Crypto.encrypt(Crypto.intToBytes(value))
                    is Long -> Crypto.encrypt(Crypto.longToBytes(value))
                    is Float -> Crypto.encrypt(Crypto.floatToBytes(value))
                    is Boolean -> Crypto.encrypt(Crypto.booleanToBytes(value))
                    is Set<*> -> Crypto.encrypt(value.joinToString(",").toByteArray())
                    else -> throw DataStoreException.InvalidTypeError(value!!::class.java.name)
                }.encodeToBase64()

                preferences[stringPreferencesKey(key)] = encryptedValue
            }
            if (config.enableCaching) {
                cacheMap.getOrPut(tableName) { ConcurrentHashMap() }.apply {
                    if (size >= config.cacheSize) remove(keys.first()) // Evict oldest item
                    put(key, value as Any)
                }
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun <T> getPreference(
        key: String,
        defaultValueType: T,
        resultCanBeNull: Boolean,
        tableName: String
    ): Resource<T> = withContext(dispatcher) {
        try {
            cacheMap[tableName]?.get(key)?.let {
                @Suppress("UNCHECKED_CAST")
                return@withContext Resource.Success(it as T)
            }

            val preferences = getDataStore(tableName).data.first()
            decryptAndConvertPreference(preferences, key, defaultValueType, tableName)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun removePreference(key: String, tableName: String): Resource<Unit> = withContext(dispatcher) {
        try {
            getDataStore(tableName).edit { preferences ->
                preferences.remove(stringPreferencesKey(key))
            }
            cacheMap[tableName]?.remove(key)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DataStoreException.StorageError(e))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> observePreference(
        key: String,
        defaultValueType: T,
        resultCanBeNull: Boolean,
        tableName: String
    ): Flow<Resource<T?>> = flow {

        cacheMap[tableName]?.get(key)?.let {
            emit(Resource.Success(it as T))
            return@flow
        }

        getDataStore(tableName).data
            .map { preferences ->
                decryptAndConvertPreference(preferences, key, defaultValueType, tableName)
            }
            .distinctUntilChanged()
            .catch { e ->
                emit(Resource.Error(DataStoreException.StorageError(e)))
            }
            .collect { emit(it) }
    }.shareIn(scope, SharingStarted.Lazily, 1)


    private fun <T> decryptAndConvertPreference(
        preferences: Preferences,
        key: String,
        defaultValue: T,
        tableName: String
    ): Resource<T> {
        val encryptedValue = preferences[stringPreferencesKey(key)]?.decodeBase64()
            ?: return Resource.Success(defaultValue)

        val decryptedBytes = Crypto.decrypt(encryptedValue)

        @Suppress("UNCHECKED_CAST")
        val value = when (defaultValue) {
            is String -> decryptedBytes.decodeToString() as T
            is Int -> Crypto.bytesToInt(decryptedBytes) as T
            is Long -> Crypto.bytesToLong(decryptedBytes) as T
            is Float -> Crypto.bytesToFloat(decryptedBytes) as T
            is Boolean -> Crypto.bytesToBoolean(decryptedBytes) as T
            is Set<*> -> decryptedBytes.decodeToString().split(",").toSet() as T
            else -> throw IllegalArgumentException("Unsupported type: ${(defaultValue!!)::class.java.name}")
        }

        cacheMap.getOrPut(tableName) { ConcurrentHashMap() }[key] = value as Any
        return Resource.Success(value)
    }

    private fun handleError(e: Exception): Resource<Nothing> = when (e) {
        is DataStoreException -> Resource.Error(e)
        is java.io.IOException -> Resource.Error(DataStoreException.StorageError(e))
        else -> Resource.Error(DataStoreException.EncryptionError(e))
    }

    private fun ByteArray.encodeToBase64(): String =
        android.util.Base64.encodeToString(this, android.util.Base64.DEFAULT)

    private fun String.decodeBase64(): ByteArray =
        android.util.Base64.decode(this, android.util.Base64.DEFAULT)
}
