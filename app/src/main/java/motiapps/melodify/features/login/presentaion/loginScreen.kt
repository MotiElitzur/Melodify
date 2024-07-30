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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import motiapps.melodify.core.common.Utils

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationRoute) {
        uiState.navigationRoute?.let { route ->
            navController.navigate(route)
            viewModel.triggerEvent(LoginEvent.NavigationHandled)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Melodify",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        LoginForm(
            email = uiState.email,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword,
            emailError = uiState.emailError,
            passwordError = uiState.passwordError,
//            confirmPasswordError = uiState.confirmPasswordError,
            isLoginEnabled = uiState.isLoginEnabled,
            isLoading = uiState.isLoading,
            onEmailChange = { viewModel.triggerEvent(LoginEvent.EmailChanged(it)) },
            onPasswordChange = { viewModel.triggerEvent(LoginEvent.PasswordChanged(it)) },
//            onConfirmPasswordChange = { viewModel.triggerEvent(LoginEvent.ConfirmPasswordChanged(it)) },
            onFieldFocusChanged = { field, isFocused ->
                viewModel.triggerEvent(LoginEvent.FieldFocusChanged(field, isFocused))
            },
            onLoginClick = { viewModel.triggerEvent(LoginEvent.LoginClicked) },
            onContinueAsGuestClick = { viewModel.triggerEvent(LoginEvent.ContinueAsGuestClicked) },
            onRegisterClick = { viewModel.triggerEvent(LoginEvent.RegisterClicked) }
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun LoginForm(
    email: String,
    password: String,
    confirmPassword: String,
    emailError: String?,
    passwordError: String?,
//    confirmPasswordError: String?,
    isLoginEnabled: Boolean,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
//    onConfirmPasswordChange: (String) -> Unit,
    onFieldFocusChanged: (LoginField, Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onContinueAsGuestClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        isError = emailError != null,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFieldFocusChanged(LoginField.EMAIL, it.isFocused) }
    )
    emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        isError = passwordError != null,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFieldFocusChanged(LoginField.PASSWORD, it.isFocused) }
    )
    passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

//    Spacer(modifier = Modifier.height(8.dp))
//
//    OutlinedTextField(
//        value = confirmPassword,
//        onValueChange = onConfirmPasswordChange,
//        label = { Text("Confirm Password") },
//        visualTransformation = PasswordVisualTransformation(),
//        isError = confirmPasswordError != null,
//        modifier = Modifier
//            .fillMaxWidth()
//            .onFocusChanged { onFieldFocusChanged(LoginField.CONFIRM_PASSWORD, it.isFocused) }
//    )
//    confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
//
//    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onLoginClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = isLoginEnabled && !isLoading
    ) {
        Text(text = "Login")
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(
        onClick = onContinueAsGuestClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Continue as Guest")
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(
        onClick = onRegisterClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Register")
    }
}