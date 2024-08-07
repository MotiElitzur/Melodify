package motiapps.melodify.core.di

import android.content.Context
import androidx.room.Room
import motiapps.melodify.core.App
import motiapps.melodify.core.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}