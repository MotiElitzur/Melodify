//package motiapps.melodify.common.permissions.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityRetainedComponent
//import dagger.hilt.android.scopes.ActivityRetainedScoped
//import melodify.datastore.domain.usecase.PreferencesUseCases
//import motiapps.melodify.common.permissions.data.PermissionManager
//import motiapps.melodify.common.permissions.domain.repository.PermissionRepository
//import motiapps.melodify.common.permissions.data.PermissionRepositoryImpl
//
//@Module
//@InstallIn(ActivityRetainedComponent::class)
//object PermissionModule {
//
//    @Provides
//    @ActivityRetainedScoped
//    fun providePermissionRepository(
//        permissionManager: PermissionManager,
//        preferencesUseCases: PreferencesUseCases
//    ): PermissionRepository {
//        return PermissionRepositoryImpl(permissionManager, preferencesUseCases)
//    }
//
//    @Provides
//    @ActivityRetainedScoped
//    fun providePermissionManager(): PermissionManager {
//        return PermissionManager()
//    }
//}