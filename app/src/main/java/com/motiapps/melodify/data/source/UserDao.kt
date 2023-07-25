package com.motiapps.melodify.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.motiapps.melodify.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getUserById(userId: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}