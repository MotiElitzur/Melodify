package motiapps.melodify.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import motiapps.melodify.R

@Composable
fun ErrorDialog(error: String, onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /* Dismiss logic */ },
        title = { Text(text = stringResource(android.R.string.dialog_alert_title)) },
        text = { Text(text = error) },
        confirmButton = {
            Button(onClick = onRetry) {
                Text(text = stringResource(android.R.string.ok))
            }
        }
    )
}