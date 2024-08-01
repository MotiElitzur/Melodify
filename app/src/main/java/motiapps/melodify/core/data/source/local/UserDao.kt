package motiapps.melodify.core.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import motiapps.melodify.core.domain.model.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE id = :userId")
    suspend fun getUserById(userId: String): UserDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDto)

    @Delete
    suspend fun deleteUser(user: UserDto)

    suspend fun deleteUser(userId: String) {
        val user = getUserById(userId)
        if (user != null) {
            deleteUser(user)
        }
    }
}