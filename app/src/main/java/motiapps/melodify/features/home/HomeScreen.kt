package motiapps.melodify.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import motiapps.melodify.features.loading.presentation.LoadingViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {

//    val uiState by viewModel.uiState.collectAsState()

    println("HomeScreen created")

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Home Screen")
    }
}