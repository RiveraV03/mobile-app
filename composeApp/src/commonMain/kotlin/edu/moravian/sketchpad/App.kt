package edu.moravian.csci215.sketchpad

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.moravian.sketchpad.ui.screens.CanvasEditorScreen
import edu.moravian.sketchpad.ui.screens.NewSketchScreen
import edu.moravian.sketchpad.ui.screens.SettingsScreen

@Composable
fun App() {
    val currentRoute = remember { mutableStateOf("home") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            when (currentRoute.value) {
                "home" -> HomeScreen(
                    onCreateNew = { currentRoute.value = "create" },
                    onViewGallery = { currentRoute.value = "gallery" },
                    onSettings = { currentRoute.value = "settings" }
                )
                "create" -> NewSketchScreen(
                    onConfirm = { currentRoute.value = "canvas" },
                    onCancel = { currentRoute.value = "home" }
                )
                "canvas" -> CanvasEditorScreen(
                    title = "Drawing",
                    onSave = { currentRoute.value = "home" },
                    onCancel = { currentRoute.value = "home" }
                )
                "gallery" -> GalleryScreen(
                    onBack = { currentRoute.value = "home" }
                )
                "view" -> ViewSketchScreen(
                    onBack = { currentRoute.value = "gallery" }
                )
                "settings" -> SettingsScreen(
                    preferencesRepo = null,
                    onBack = { currentRoute.value = "home" }
                )
                else -> HomeScreen(
                    onCreateNew = { currentRoute.value = "create" },
                    onViewGallery = { currentRoute.value = "gallery" },
                    onSettings = { currentRoute.value = "settings" }
                )
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}