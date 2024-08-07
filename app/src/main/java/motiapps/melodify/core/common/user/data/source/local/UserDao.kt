package motiapps.melodify.core.common.user.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import motiapps.melodify.core.common.user.data.model.UserDto
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE id = :userId")
    suspend fun getUserById(userId: String): UserDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDto)

    @Transaction
    suspend fun updateUserFields(id: String, fields: Map<String, Any?>) {
        val user = getUserById(id)
        if (user != null) {
            val updatedUser = user.copy().apply {
                fields.forEach { (fieldName, value) ->
                    val property = this::class.declaredMemberProperties.find { it.name == fieldName }
                    property?.let {
                        it.isAccessible = true
                        val mutableProperty = it as? KMutableProperty1<UserDto, Any?>
                        mutableProperty?.set(this, value)
                    }
                }
            }
            updateUser(updatedUser)
        }
    }

    @Update
    suspend fun updateUser(user: UserDto)

    @Delete
    suspend fun deleteUser(user: UserDto)

    suspend fun deleteUser(userId: String) {
        val user = getUserById(userId)
        if (user != null) {
            deleteUser(user)
        }
    }
}