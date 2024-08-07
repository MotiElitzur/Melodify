package motiapps.melodify.features.login.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import motiapps.melodify.features.login.data.LoginRepositoryImpl
import motiapps.melodify.features.login.domain.repository.LoginRepository

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository {
        return LoginRepositoryImpl(firebaseAuth)
    }
}