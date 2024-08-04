package motiapps.melodify.core.domain.model

data class UserPartialUpdate(
    val userId: String,
    val fields: Map<String, Any>
)