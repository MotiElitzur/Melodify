package motiapps.melodify.core.data.model

import motiapps.melodify.core.domain.model.UserDto

data class User(
    val id: String,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
)

fun User.toDto() = UserDto(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
)
