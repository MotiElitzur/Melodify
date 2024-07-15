package com.motiapps.melodify.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.motiapps.melodify.features.home.HomeScreen

@Composable
//fun NavGraph(navControllerManager: NavControllerManager) {
//    val navController = rememberNavController()
//    navControllerManager.setupWithNavController(navController)
//    val startDestination: String = NavDirections.Home.route

fun NavGraph(startDestination: NavDirections) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.route) {

        composable(NavDirections.Login.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(NavDirections.Loading.route) {
            HomeScreen(
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