package motiapps.melodify.features.loading.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import motiapps.melodify.core.common.user.domain.repository.UserRepository
import motiapps.melodify.features.loading.data.LoadingRepositoryImpl
import motiapps.melodify.features.loading.domain.LoadingRepository

@Module
@InstallIn(ViewModelComponent::class)
object LoadingModule {

    @Provides
    @ViewModelScoped
    fun provideLoadingRepository(
        userRepository: UserRepository
    ): LoadingRepository {
        return LoadingRepositoryImpl(userRepository)
    }
}