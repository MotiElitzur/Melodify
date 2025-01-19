package motiapps.melodify.core.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import motiapps.melodify.core.presentation.navigation.NavGraph
import motiapps.melodify.features.splash.SplashViewModel
import motiapps.melodify.core.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import motiapps.melodify.common.Logger
import motiapps.melodify.core.data.lifecycle.ActivityContextProvider
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var activityContextProvider: ActivityContextProvider

    // This is better way then inject for viewModels in activity.
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.log("MainActivity created")

        // Install the splash screen before on create and set the condition to keep it visible
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.state.isLoading
        }

        super.onCreate(savedInstanceState)
        lifecycle.addObserver(activityContextProvider)

        setContent {
            AppTheme {
                // Observe the initial route and navigate to it.
                val state by splashViewModel.uiState.collectAsState()
                Logger.log("MainActivity: $state")

                state.initialRoute?.let { initialRoute ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Logger.log("Initial route: $initialRoute")
                        NavGraph(initialRoute = initialRoute)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    fun updateLocale(languageTag: String) {
        // write change app language code here
        val config = resources.configuration
        val locale = Locale(languageTag)
        Locale.setDefault(locale)
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save for android 13+
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))

        // Restart the activity with new flags.
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        // Finish and start the activity to refresh the app.
        finish()
        startActivity(intent)
    }

}