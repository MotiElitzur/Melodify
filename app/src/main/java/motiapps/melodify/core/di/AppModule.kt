package motiapps.melodify.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import motiapps.melodify.core.App
import motiapps.melodify.core.data.repository.base.DataRepository
import motiapps.melodify.core.data.repository.impl.AuthRepositoryImpl
import motiapps.melodify.core.data.repository.impl.UserRepositoryImpl
import motiapps.melodify.core.data.source.AppDatabase
import motiapps.melodify.core.data.source.Sources
import motiapps.melodify.core.domain.repository.AuthRepository
import motiapps.melodify.core.domain.repository.UserRepository
import motiapps.melodify.core.domain.usecases.GetUserUseCase
import motiapps.melodify.core.domain.usecases.InsertUserUseCase
import motiapps.melodify.core.domain.usecases.UserUseCases
import motiapps.melodify.core.presentation.navigation.NavControllerManager
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
    fun provideNavControllerManager(): NavControllerManager {
        return NavControllerManager()
    }

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepository()
    }

//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
//        return AuthRepositoryImpl(firebaseAuth)
//    }

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