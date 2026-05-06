package edu.moravian.sketchpad.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * New Sketch screen: lets user enter a sketch title and confirm before entering the canvas editor.
 */
@Composable
fun NewSketchScreen(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
) {
    var title by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("New Sketch", style = MaterialTheme.typography.headlineSmall)

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Enter sketch title") },
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = { if (title.isNotBlank()) onConfirm(title) },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank(),
        ) {
            Text("Start Drawing")
        }

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Cancel")
        }
    }
}