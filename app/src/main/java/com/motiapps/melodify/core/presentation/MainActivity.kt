package com.motiapps.melodify.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.motiapps.melodify.core.presentation.navigation.NavGraph
import com.motiapps.melodify.features.splash.SplashViewModel
import com.motiapps.melodify.core.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Handle the splash screen transition.
        val appSplashScreen = installSplashScreen()
        println("appSplashScreen: $appSplashScreen")

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(splashViewModel) {
                        splashViewModel.navigationEvent.collect { event ->
                            if (event != null) {
                                appSplashScreen.setKeepOnScreenCondition { false }
                            }
                        }
                    }
                    NavGraph()
                }
            }
        }

        appSplashScreen.setKeepOnScreenCondition { true }
    }
}

//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//    Column(
//        modifier = modifier
//            .background(Color.Green)
//            .fillMaxSize()
//            .padding(32.dp)
//            .border(2.dp, Color.Blue),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Center
//
//    ) {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//                .offset(100.dp, (-50).dp)
//        )
//
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(
//                    color = Color.Red
//                )
//        )
//        Text(
//            text = "Hello $name!",
//
//            )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MelodifyTheme {
//        Greeting("Moti")
//    }
//}