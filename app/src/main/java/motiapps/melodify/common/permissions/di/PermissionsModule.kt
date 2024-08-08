package motiapps.melodify.common.permissions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.permissions.domain.repository.PermissionRepository
import motiapps.melodify.common.permissions.data.PermissionRepositoryImpl
import javax.inject.Inject

@ActivityRetainedScoped
class PermissionRepositoryFactory @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases
) {
    fun create(activityContextProvider: ActivityContextProvider): PermissionRepository {
        return PermissionRepositoryImpl(activityContextProvider, preferencesUseCases)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object PermissionModule {
    @Provides
    @ActivityRetainedScoped
    fun providePermissionRepository(
        factory: PermissionRepositoryFactory,
        activityContextProvider: ActivityContextProvider
    ): PermissionRepository {
        return factory.create(activityContextProvider)
    }
}