package com.motiapps.melodify.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.motiapps.melodify.presentation.home.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination: String = NavDirections.Home.route

    NavHost(navController = navController, startDestination = startDestination) {

//        composable(NavDirections.Splash.route) {
//            SplashScreen(
//                viewModel = hiltViewModel(),
//                navController = navController
//            )
//        }

        composable(NavDirections.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}