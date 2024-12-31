package motiapps.melodify.common.language.data

import androidx.activity.ComponentActivity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import jakarta.inject.Inject
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.language.domain.usecase.LanguageUseCases
import motiapps.melodify.core.data.lifecycle.ActivityContextProvider
import motiapps.melodify.core.domain.base.Resource
import java.util.Locale

@ActivityRetainedScoped
class LanguageManager @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    private val activityContextProvider: ActivityContextProvider
) {

    /**
     * Apply the language based on user preferences when the activity context is set.
     */
    suspend fun applyLanguageFromPreferences() {
        val activity = activityContextProvider.activity

        // Get the preferred language from use case
        val result = preferencesUseCases.getPreferenceUseCase(PreferenceObject("appLanguage", "en"))
        val preferredLanguage = (result as? Resource.Success)?.data as? String ?: "en"

        // Change the application's language
        changeAppLanguage(activity, preferredLanguage)
    }

    private fun changeAppLanguage(activity: ComponentActivity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = activity.resources.configuration
        config.setLocale(locale)
        activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
    }
}