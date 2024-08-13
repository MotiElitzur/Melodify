package motiapps.melodify.common.user.data.model

import com.google.firebase.Timestamp
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

data class User(
    val id: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var creationTimestamp: Timestamp? = null,
    var lastActive: Timestamp? = null,
    var isAnonymous: Boolean = false
)

fun User.toDto() = UserDto(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    creationTimestamp = creationTimestamp,
    lastActive = lastActive,
    isAnonymous = isAnonymous
)

fun User.update(updates: Map<String, Any>) {
    val userClass = this::class
    for ((key, value) in updates) {
        val property = userClass.memberProperties.find { it.name == key }
        if (property != null && property is kotlin.reflect.KMutableProperty<*>) {
            property.isAccessible = true
            property.setter.call(this, value)
        }
    }
}
