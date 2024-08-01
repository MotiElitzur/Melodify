package motiapps.melodify.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import motiapps.melodify.core.domain.model.UserDto

@Database(entities = [UserDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "melodify_db"
        const val USER_TABLE_NAME = "user"
    }
}