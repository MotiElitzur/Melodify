package com.motiapps.melodify.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.motiapps.melodify.core.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "melodify_db"
        const val USER_TABLE_NAME = "user"
    }
}