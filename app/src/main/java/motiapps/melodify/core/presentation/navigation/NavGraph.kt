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
//fun NavGraph(navControllerManager: NavControllerManager) {
//    val navController = rememberNavController()
//    navControllerManager.setupWithNavController(navController)
//    val startDestination: String = NavDirections.Home.route

fun NavGraph(startDestination: NavDirections) {
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
    }
}