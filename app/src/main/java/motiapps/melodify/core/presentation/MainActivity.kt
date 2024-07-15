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
import motiapps.melodify.core.presentation.navigation.NavControllerManager
import motiapps.melodify.core.presentation.navigation.NavGraph
import motiapps.melodify.features.splash.SplashViewModel
import motiapps.melodify.core.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var navControllerManager: NavControllerManager
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Install the splash screen and set the condition to keep it visible
        installSplashScreen().setKeepOnScreenCondition {
//            splashViewModel.initialRoute.value == null
            val keepOnScreen = splashViewModel.initialRoute.value == null
            println("Keep splash screen on: $keepOnScreen, initialRoute: ${splashViewModel.initialRoute.value}")
            keepOnScreen
        }

        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {

                val initialRoute by splashViewModel.initialRoute.collectAsState()

                if (initialRoute != null) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(startDestination = initialRoute!!)
//                    NavGraph(navControllerManager = navControllerManager)
                    }
                }
            }
        }
    }
}