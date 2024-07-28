package motiapps.melodify.features.login.presentaion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import motiapps.melodify.core.common.Utils
import motiapps.melodify.features.login.LoginData

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    val state by viewModel.uiState.collectAsState()

    println("LoginScreen: $state, ")

    // Use remember for email and password
    var email by remember { mutableStateOf(state.email ?: "") }
    var password by remember { mutableStateOf(state.password ?: "") }

    // Use remember for loginEnabled
    val isLoginEnabled by remember(email, password) {
        derivedStateOf { Utils.isEmailValid(email) && Utils.isPasswordValid(password) }
    }

    // Handle navigation in a LaunchedEffect
    LaunchedEffect(state.route) {
        println("LoginScreen: LaunchedEffect: ${state.route}")
        state.route?.let { route ->

            navController.navigate(route)
            viewModel.triggerEvent(LoginEvent.SaveToNextState)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Screen", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        EmailTextField(email) { email = it
        viewModel.triggerEvent(LoginEvent.SetLoginDataState(LoginData(email, password)))
        }

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(password) { password = it
            viewModel.triggerEvent(LoginEvent.SetLoginDataState(LoginData(email, password)))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginButton(
            isEnabled = isLoginEnabled,
            isLoading = state.isLoading,
            onClick = {
                viewModel.triggerEvent(LoginEvent.SetLoginDataState(LoginData(email, password)))

            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ContinueAsGuestButton(viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        RegisterButton(viewModel)

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = state.error!!)
        }
    }
}

@Composable
private fun EmailTextField(email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LoginButton(isEnabled: Boolean, isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = isEnabled && !isLoading
    ) {
        Text(text = "Login")
    }
}

@Composable
private fun ContinueAsGuestButton(viewModel: LoginViewModel) {
    TextButton(
        onClick = {
            viewModel.triggerEvent(LoginEvent.SetContinueAsGuestState)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Continue without login")
    }
}

@Composable
private fun RegisterButton(viewModel: LoginViewModel) {
    TextButton(
        onClick = {
            println("Register pressed")
            viewModel.triggerEvent(LoginEvent.SetStartRegister)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Register")
    }
}
