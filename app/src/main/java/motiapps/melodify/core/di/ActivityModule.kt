package motiapps.melodify.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.language.data.LanguageManager
import motiapps.melodify.common.permissions.data.PermissionManager
import motiapps.melodify.core.data.lifecycle.ActivityContextProvider

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

//    @Provides
//    @ActivityRetainedScoped
//    fun provideActivityContextProvider(): ActivityContextProvider {
//        return ActivityContextProvider()
//    }

    @Provides
    @ActivityRetainedScoped
    fun provideActivityContextProvider(
        permissionManager: PermissionManager,
        languageManager: LanguageManager
    ): ActivityContextProvider {
        return ActivityContextProvider(permissionManager, languageManager)
    }
}