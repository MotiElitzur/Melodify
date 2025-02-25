package motiapps.melodify.common.language.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import melodify.datastore.domain.model.DataStoreItem
import melodify.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.language.domain.repository.LanguageRepository
import melodify.core.domain.Resource
import java.util.Locale
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    val preferencesUseCases: PreferencesUseCases
) : LanguageRepository {

    override suspend fun setAppLanguage(languageTag: String): Resource<Unit> {
        return try {
            withContext(Dispatchers.Main) {
                val locale = Locale(languageTag)
                val config = context.resources.configuration

                Locale.setDefault(locale)
                config.setLocale(locale)

                context.resources.updateConfiguration(config, context.resources.displayMetrics)
                context.createConfigurationContext(config)

                // For Android 13 and above
                val localeList = LocaleListCompat.forLanguageTags(languageTag)
                AppCompatDelegate.setApplicationLocales(localeList)

                // Save the language to preferences
                preferencesUseCases.setPreferenceUseCase(DataStoreItem("appLanguage", languageTag))
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getAppLanguage(): Resource<String> {
        return try {
            val languageCode = withContext(Dispatchers.Main) {
                val locales = AppCompatDelegate.getApplicationLocales()
                if (!locales.isEmpty) {
                    locales[0]?.language ?: Locale.getDefault().language
                } else {
                    Locale.getDefault().language
                }
            }
            Resource.Success(languageCode)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}

