package motiapps.melodify.features.register.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import motiapps.melodify.core.presentation.navigation.NavDirections

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateTo) {
        uiState.navigateTo?.let { route ->
            navController.navigate(route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegistrationFields(
            fields = uiState.registrationFields,
            fieldErrors = uiState.fieldErrors,
            onFieldChange = { field, value ->
                viewModel.triggerEvent(RegisterEvent.UpdateField(field, value))
            },
            onFieldFocusLost = { field ->
                viewModel.triggerEvent(RegisterEvent.ValidateField(field))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.triggerEvent(RegisterEvent.SubmitRegistration) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                navController.navigate(NavDirections.Login.route) {
                    popUpTo(NavDirections.Register.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to Login Screen")
        }

        uiState.generalError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun RegistrationFields(
    fields: Map<RegistrationField, String>,
    fieldErrors: Map<RegistrationField, String>,
    onFieldChange: (RegistrationField, String) -> Unit,
    onFieldFocusLost: (RegistrationField) -> Unit
) {
    val fieldConfigs = listOf(
        RegistrationField.FirstName to "First Name",
        RegistrationField.LastName to "Last Name",
        RegistrationField.Email to "Email",
        RegistrationField.Password to "Password",
        RegistrationField.ConfirmPassword to "Confirm Password"
    )

    fieldConfigs.forEach { (field, label) ->
        var isFocused by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = fields[field] ?: "",
            onValueChange = { onFieldChange(field, it) },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (isFocused && !focusState.isFocused) {
                        onFieldFocusLost(field)
                    }
                    isFocused = focusState.isFocused
                },
            isError = fieldErrors.containsKey(field),
            keyboardOptions = when (field) {
                RegistrationField.Email -> KeyboardOptions(keyboardType = KeyboardType.Email)
                RegistrationField.Password, RegistrationField.ConfirmPassword ->
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                else -> KeyboardOptions.Default
            },
            visualTransformation = when (field) {
                RegistrationField.Password, RegistrationField.ConfirmPassword ->
                    PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            supportingText = {
                fieldErrors[field]?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}