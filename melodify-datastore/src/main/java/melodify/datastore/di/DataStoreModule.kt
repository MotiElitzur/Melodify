package melodify.datastore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import melodify.datastore.data.PreferencesDataStoreRepositoryImpl
import melodify.datastore.domain.repository.PreferencesDataStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context): PreferencesDataStoreRepository {
        return PreferencesDataStoreRepositoryImpl(context)
    }
}