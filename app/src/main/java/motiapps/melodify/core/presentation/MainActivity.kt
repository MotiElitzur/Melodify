package motiapps.melodify.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import motiapps.melodify.core.presentation.navigation.NavGraph
import motiapps.melodify.features.splash.SplashViewModel
import motiapps.melodify.core.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import motiapps.melodify.common.permissions.di.ActivityContextProvider
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var activityContextProvider: ActivityContextProvider

    // This is better way then inject for viewModels in activity.
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        println("MainActivity created")

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
                println("MainActivity: $state")

                state.initialRoute?.let { initialRoute ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        println("Initial route: $initialRoute")
                        NavGraph(initialRoute = initialRoute)
                    }
                }
            }
        }
    }
}