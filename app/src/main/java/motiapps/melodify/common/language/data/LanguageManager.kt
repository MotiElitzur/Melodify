package motiapps.melodify.common.language.data

import androidx.activity.ComponentActivity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import melodify.core.domain.Resource
import melodify.datastore.domain.model.DataStoreItem
import melodify.datastore.domain.usecase.PreferencesUseCases
import melodify.core.domain.lifecycle.ActivityContextProvider
import java.util.Locale

@ActivityRetainedScoped
class LanguageManager @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
): ActivityContextProvider.LifecycleListener {

    private var activity: ComponentActivity? = null

    override fun onCreate(activity: ComponentActivity) {
        this.activity = activity

        CoroutineScope(Dispatchers.IO).launch {

            // Get the preferred language from use case
            val result = preferencesUseCases.getPreferenceUseCase(DataStoreItem("appLanguage", "en"))
            val preferredLanguage = (result as? Resource.Success)?.data as? String ?: "en"

            // Change the application's language
            changeAppLanguage(activity, preferredLanguage)
        }
    }

    private fun changeAppLanguage(activity: ComponentActivity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = activity.resources.configuration
        config.setLocale(locale)
        activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
    }
}