package motiapps.melodify.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import motiapps.melodify.core.data.repository.impl.AuthRepositoryImpl
import motiapps.melodify.core.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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


//    @Provides
//    @Singleton
//    fun provideAuthService(firebaseAuth: FirebaseAuth): AuthService {
//        return AuthService(firebaseAuth)
//    }
}