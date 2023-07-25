package com.motiapps.melodify.di

import android.content.Context
import androidx.room.Room
import com.motiapps.melodify.App
import com.motiapps.melodify.data.repository.base.DataRepository
import com.motiapps.melodify.data.repository.impl.UserRepositoryImpl
import com.motiapps.melodify.data.source.AppDatabase
import com.motiapps.melodify.data.source.Sources
import com.motiapps.melodify.domain.repository.UserRepository
import com.motiapps.melodify.domain.usecases.GetUserUseCase
import com.motiapps.melodify.domain.usecases.InsertUserUseCase
import com.motiapps.melodify.domain.usecases.UserUseCases
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
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepository()
    }

//    @Provides
//    fun provideUserViewModel(
//        dataRepository: DataRepository,
//    ): DataViewModel {
//        return DataViewModel(dataRepository)
//    }


    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideSources(
        appDatabase: AppDatabase,
    ): Sources {

        return Sources(
            appDatabase,
            "firestore?"
        )
    }

    @Provides
    @Singleton
    fun provideGetUserRepository(
        appDatabase: AppDatabase,
    ): UserRepository =
        UserRepositoryImpl(appDatabase.userDao())

    @Provides
    fun provideUserUseCases(
        userRepository: UserRepository,
    ): UserUseCases {
        return UserUseCases(
            getUser = GetUserUseCase(userRepository),
            insertUser = InsertUserUseCase(userRepository),
        )
    }

}