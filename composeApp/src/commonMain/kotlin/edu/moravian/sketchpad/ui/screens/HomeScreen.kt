package edu.moravian.sketchpad.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Home screen: displays app overview, quick access to create a new sketch or view recent sketches.
 */
@Composable
fun HomeScreen(
    onCreateNew: () -> Unit,
    onViewGallery: () -> Unit,
    onSettings: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("SketchPad", style = MaterialTheme.typography.headlineLarge)
        Text("Create and manage your digital artwork", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCreateNew,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Create New Sketch")
        }

        TextButton(onClick = onViewGallery) {
            Text("View Gallery")
        }

        TextButton(onClick = onSettings) {
            Text("Settings")
        }
    }
}