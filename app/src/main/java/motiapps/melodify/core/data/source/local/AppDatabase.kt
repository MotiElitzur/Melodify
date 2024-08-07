package motiapps.melodify.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import motiapps.melodify.core.common.user.data.source.local.UserDao
import motiapps.melodify.core.data.source.Converters
import motiapps.melodify.core.common.user.data.model.UserDto

@Database(entities = [UserDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "melodify_db"
        const val USER_TABLE_NAME = "user"
    }
}