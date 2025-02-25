package motiapps.melodify.common.notifications.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import motiapps.melodify.common.notifications.data.repository.NotificationRepositoryImpl
import motiapps.melodify.common.notifications.data.repository.NotificationsBroadcastReceiver
import motiapps.melodify.common.notifications.domain.repository.NotificationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModel {

    @Singleton
    @Provides
    fun provideNotificationRepository(
        context: Context,
    ): NotificationRepository {
        return NotificationRepositoryImpl(context)
    }

//    @Singleton
//    @Provides
//    fun provideNotificationBroadcastReceiver(
//        preferencesUseCases: PreferencesUseCases
//    ): NotificationsBroadcastReceiver {
//        return NotificationsBroadcastReceiver(preferencesUseCases)
//    }
}