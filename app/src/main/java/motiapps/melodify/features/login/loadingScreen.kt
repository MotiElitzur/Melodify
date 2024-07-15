package motiapps.melodify.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import motiapps.melodify.features.loading.LoadingViewModel

@Composable
fun LoginScreen(viewModel: LoadingViewModel, navController: NavController) {

    println("LoginScreen created")

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Login Screen")
    }
}