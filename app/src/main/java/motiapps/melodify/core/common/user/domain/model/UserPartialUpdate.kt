package motiapps.melodify.core.common.user.domain.model

data class UserPartialUpdate(
    val userId: String,
    val fields: Map<String, Any>
)