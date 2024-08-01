package motiapps.melodify.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.core.data.source.remote.FirebaseDataSource
import motiapps.melodify.core.data.source.remote.FirebaseDataSourceImpl
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
    ): FirebaseDataSource {
        return FirebaseDataSourceImpl(firebaseAuth, firestore)
    }


//    @Provides
//    @Singleton
//    fun provideAuthService(firebaseAuth: FirebaseAuth): AuthService {
//        return AuthService(firebaseAuth)
//    }
}