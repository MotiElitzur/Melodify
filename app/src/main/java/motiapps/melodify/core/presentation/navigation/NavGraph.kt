package motiapps.melodify.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import motiapps.melodify.features.home.HomeScreen
import motiapps.melodify.features.loading.LoadingScreen
import motiapps.melodify.features.login.LoginScreen

@Composable
fun NavGraph(initialRoute: String) {

    val startDestination = NavDirections.fromName(initialRoute)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.route) {

        composable(NavDirections.Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(NavDirections.Loading.route) {
            LoadingScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(NavDirections.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        // there is a way to separate the navigation logic from the screen using navigation parts.
//        navigation(
//            route = NavDirections.HomeFeature.route,
//            startDestination = NavDirections.Home.route
//        ) {
//            composable(NavDirections.Home.route) {
//                HomeScreen(
//                    viewModel = hiltViewModel(),
//                    navController = navController
//                )
//            }
//        }
    }
}