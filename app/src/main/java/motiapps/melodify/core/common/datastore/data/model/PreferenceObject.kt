package motiapps.melodify.core.common.datastore.data.model

data class PreferenceObject<T>(
    val key: String,
    val value: T
)