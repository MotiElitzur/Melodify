package motiapps.melodify.common.language.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import melodify.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.language.data.LanguageManager
import motiapps.melodify.common.language.data.LanguageRepositoryImpl
import motiapps.melodify.common.language.domain.repository.LanguageRepository

@Module
@InstallIn(SingletonComponent::class)
object LanguageModule {

    @Provides
    fun provideLanguageRepository(
        @ApplicationContext context: Context,
        preferencesUseCases: PreferencesUseCases
    ): LanguageRepository = LanguageRepositoryImpl(context, preferencesUseCases)

    @Provides
    fun provideLanguageManager(
        preferencesUseCases: PreferencesUseCases
    ): LanguageManager {
        return LanguageManager(preferencesUseCases)
    }

}