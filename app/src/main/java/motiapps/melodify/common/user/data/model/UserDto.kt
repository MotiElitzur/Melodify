package motiapps.melodify.common.user.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity
data class UserDto(
    @PrimaryKey val id: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var creationTimestamp: Timestamp? = null,
    var lastActive: Timestamp? = null,
    var isAnonymous: Boolean = true
) {
    // No-argument constructor for deserialization
    constructor() : this("")
}

fun UserDto.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    creationTimestamp = creationTimestamp,
    lastActive = lastActive,
)