package motiapps.melodify.core.common.permissions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.core.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.core.common.permissions.domain.repository.PermissionRepository
import motiapps.melodify.core.common.permissions.data.PermissionRepositoryImpl
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