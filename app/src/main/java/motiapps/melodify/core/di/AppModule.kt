package motiapps.melodify.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import motiapps.melodify.core.App
import motiapps.melodify.core.data.repository.impl.AuthRepositoryImpl
import motiapps.melodify.core.data.repository.impl.UserRepositoryImpl
import motiapps.melodify.core.data.source.AppDatabase
import motiapps.melodify.core.data.source.Sources
import motiapps.melodify.core.domain.repository.AuthRepository
import motiapps.melodify.core.domain.repository.UserRepository
import motiapps.melodify.core.domain.usecases.GetUserUseCase
import motiapps.melodify.core.domain.usecases.InsertUserUseCase
import motiapps.melodify.core.domain.usecases.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.core.data.repository.impl.LoginRepositoryImpl
import motiapps.melodify.core.domain.repository.LoginRepository
import motiapps.melodify.core.domain.repository.RegisterRepository
import motiapps.melodify.features.register.data.RegisterRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

//    @Provides
//    @Singleton
//    fun provideErrorHandler(@ApplicationContext context: Context): ErrorHandler {
//        return ErrorHandler(context)
//    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository {
        return LoginRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): RegisterRepository {
        return RegisterRepositoryImpl(firebaseAuth, firestore)
    }

//    @Provides
//    @Singleton
//    fun provideNavControllerManager(): NavControllerManager {
//        return NavControllerManager()
//    }

//    @Provides
//    @Singleton
//    fun provideDataRepository(): DataRepository {
//        return DataRepository()
//    }

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