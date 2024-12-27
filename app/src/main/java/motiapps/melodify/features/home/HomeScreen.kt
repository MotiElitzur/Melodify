package motiapps.melodify.features.home

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import motiapps.melodify.R
import motiapps.melodify.common.Logger
import motiapps.melodify.core.presentation.MainActivity
import motiapps.melodify.features.loading.presentation.LoadingViewModel
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val currentLanguage by viewModel.currentLanguage
    val context = LocalContext.current

    // Log the current language at the start of composition
    Logger.d("HomeScreen", "Current language at start: $currentLanguage")

    // Create a localized context dynamically based on current language
    val localizedContext = remember(currentLanguage) {
        context.createConfigurationContext(Configuration(context.resources.configuration).apply {
            setLocale(Locale(currentLanguage))
        })
    }

    // Fetch strings using the localized context
    val currentLanguageText = remember(localizedContext) {
        localizedContext.resources.getString(
            R.string.current_language_label,
            currentLanguage
        )
    }.also {
        Logger.d("HomeScreen", "Localized string for current language: $it")
    }

    val buttonText = remember(localizedContext) {
        localizedContext.resources.getString(
            if (currentLanguage == "en") R.string.switch_to_spanish else R.string.switch_to_english
        )
    }.also {
        Logger.d("HomeScreen", "Localized string for button: $it")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentLanguageText,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newLanguage = if (currentLanguage == "en") "es" else "en"
                Logger.d("HomeScreen", "Language change button clicked. Changing to: $newLanguage")
                viewModel.changeLanguage(newLanguage)
            }
        ) {
            Text(text = buttonText)
        }
    }
}
