package motiapps.melodify.features.home

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import motiapps.melodify.R
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val context = LocalContext.current

    // Wrap the context with the updated configuration
    val localizedContext = remember(currentLanguage) {
        context.createConfigurationContext(Configuration(context.resources.configuration).apply {
            setLocale(Locale(currentLanguage))
        })
    }

    // Fetch localized strings using the wrapped context
    val buttonText = if (currentLanguage == "en") {
        localizedContext.resources.getString(R.string.switch_to_spanish)
    } else {
        localizedContext.resources.getString(R.string.switch_to_english)
    }

    // Determine if the permissions button should be shown
    val showPermissionButton = remember {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp), // Increased top/bottom padding
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Greeting Section
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            userName?.let {
                Text(
                    text = stringResource(id = R.string.welcome_user, it),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(8.dp)
                )
            } ?: Text(
                text = stringResource(id = R.string.welcome_guest),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = stringResource(id = R.string.current_language_label, currentLanguage),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons Section
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
//            // Language Switch Button
//            Button(
//                onClick = {
//                    val newLanguage = if (currentLanguage == "en") "es" else "en"
//                    viewModel.changeLanguage(newLanguage)
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = buttonText)
//            }

            // Permissions Button (shown only if needed)
            if (showPermissionButton) {
                Button(
                    onClick = {
                        viewModel.askForPermissions()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.ask_permissions))
                }
            }

            // Notification Button
            Button(
                onClick = {
                    viewModel.showNotification()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.show_notification))
            }

            // Dark Mode Toggle Button
            Button(
                onClick = {
                    viewModel.toggleDarkMode()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isDarkMode) {
                        stringResource(id = R.string.switch_to_light_mode)
                    } else {
                        stringResource(id = R.string.switch_to_dark_mode)
                    }
                )
            }
        }
    }
}