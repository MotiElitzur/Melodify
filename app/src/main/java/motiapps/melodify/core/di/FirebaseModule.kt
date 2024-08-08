package motiapps.melodify.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.common.user.domain.source.UserRemoteDataSource
import motiapps.melodify.common.user.data.source.remote.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(firebaseAuth, firestore)
    }
}