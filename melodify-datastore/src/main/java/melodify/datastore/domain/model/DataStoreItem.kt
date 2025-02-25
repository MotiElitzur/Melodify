package melodify.datastore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DataStoreItem<T>(
    val key: String,
    val value: T,
    val isResultCanBeNull: Boolean = false,
    val tableName: String = "preferences"
)