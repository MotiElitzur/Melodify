package motiapps.melodify.common.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.common.user.data.repository.UserRepositoryImpl
import motiapps.melodify.common.firebase.user.domain.repository.UserRemoteDataSource
import motiapps.melodify.common.user.domain.repository.UserRepository
import motiapps.melodify.core.data.source.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideGetUserRepository(
        appDatabase: AppDatabase,
        remoteDataBase: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(appDatabase.userDao(), remoteDataBase)
    }
}


