package motiapps.melodify.common.firebase.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.common.firebase.domain.FirestoreProvider
import motiapps.melodify.common.firebase.user.domain.repository.UserRemoteDataSource
import motiapps.melodify.common.firebase.user.data.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        firestoreProvider: FirestoreProvider
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(firebaseAuth, firestoreProvider)
    }
}

