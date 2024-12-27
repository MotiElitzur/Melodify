package motiapps.melodify.common.language.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.common.language.data.LanguageRepositoryImpl
import motiapps.melodify.common.language.domain.usecase.ChangeLanguageUseCase
import motiapps.melodify.common.language.domain.usecase.GetLanguageUseCase
import motiapps.melodify.common.language.domain.repository.LanguageRepository

@Module
@InstallIn(SingletonComponent::class)
object LanguageModule {

//    @Provides
//    fun provideLanguageRepository(): LanguageRepository = LanguageRepositoryImpl()

    @Provides
    fun provideLanguageRepository(
        @ApplicationContext context: Context
    ): LanguageRepository = LanguageRepositoryImpl(context)

    @Provides
    fun provideChangeLanguageUseCase(repository: LanguageRepository): ChangeLanguageUseCase {
        return ChangeLanguageUseCase(repository)
    }

    @Provides
    fun provideGetLanguageUseCase(repository: LanguageRepository): GetLanguageUseCase {
        return GetLanguageUseCase(repository)
    }
}