package motiapps.melodify.features.register.data.model

data class CreateUserInput(
    val userId: String,
    val firstName: String? = null,
    val lastName: String? = null,
)