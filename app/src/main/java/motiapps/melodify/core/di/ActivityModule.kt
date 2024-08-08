package motiapps.melodify.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.permissions.di.ActivityContextProvider


@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun provideActivityContextProvider(): ActivityContextProvider {
        return ActivityContextProvider()
    }

}