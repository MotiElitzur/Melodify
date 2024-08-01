package motiapps.melodify.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import motiapps.melodify.core.App
import motiapps.melodify.core.data.repository.AuthRepositoryImpl
import motiapps.melodify.core.data.repository.UserRepositoryImpl
import motiapps.melodify.core.data.source.local.AppDatabase
import motiapps.melodify.core.domain.repository.AuthRepository
import motiapps.melodify.core.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.core.data.source.remote.FirebaseDataSource
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

    @Provides
    @Singleton
    fun provideGetUserRepository(
        appDatabase: AppDatabase,
        remoteDataBase: FirebaseDataSource
    ): UserRepository {
        return UserRepositoryImpl(appDatabase.userDao(), remoteDataBase)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}