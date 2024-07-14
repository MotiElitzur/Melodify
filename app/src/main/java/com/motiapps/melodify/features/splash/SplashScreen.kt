//package com.motiapps.melodify.presentation.splash
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.State
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavController
//import com.motiapps.melodify.navigation.NavDirections
//
//@Composable
//fun SplashScreen(viewModel: SplashViewModel, navController: NavController) {
//
//    val navigationEvent: State<NavDirections?> = viewModel.navigationEvent.collectAsState()
//
//    LaunchedEffect(key1 = navigationEvent.value) {
//        navigationEvent.value?.let { navDirection ->
//            navController.navigate(navDirection.route)
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
//        Text(text = "Splash Screen")
//    }
//}