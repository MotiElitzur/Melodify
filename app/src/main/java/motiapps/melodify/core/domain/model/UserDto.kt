package motiapps.melodify.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import motiapps.melodify.core.data.model.User

@Entity
data class UserDto(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val creationTimestamp: Long? = null,
)

fun UserDto.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
)