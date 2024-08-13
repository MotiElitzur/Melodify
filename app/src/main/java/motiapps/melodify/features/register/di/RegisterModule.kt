package motiapps.melodify.features.register.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import motiapps.melodify.common.user.domain.usecases.UserUseCases
import motiapps.melodify.features.login.data.LoginRepositoryImpl
import motiapps.melodify.features.login.domain.repository.LoginRepository
import motiapps.melodify.features.register.data.repository.RegisterRepositoryImpl
import motiapps.melodify.features.register.domain.repository.RegisterRepository

@Module
@InstallIn(ViewModelComponent::class)
object RegisterModule {

    @Provides
    @ViewModelScoped
    fun provideRegisterRepository(firebaseAuth: FirebaseAuth, userUseCases: UserUseCases): RegisterRepository {
        return RegisterRepositoryImpl(firebaseAuth, userUseCases)
    }
}