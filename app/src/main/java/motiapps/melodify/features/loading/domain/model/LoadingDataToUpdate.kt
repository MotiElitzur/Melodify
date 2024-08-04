package motiapps.melodify.features.loading.domain.model

data class LoadingDataToUpdate(
    val userId: String? = null,
    val dataToUpdate: Map<String, Any>? = null
) 