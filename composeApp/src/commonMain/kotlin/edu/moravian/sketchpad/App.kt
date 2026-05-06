package edu.moravian.sketchpad

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.moravian.sketchpad.ui.screens.CanvasEditorScreen
import edu.moravian.sketchpad.ui.screens.HomeScreen
import edu.moravian.sketchpad.ui.screens.NewSketchScreen
import edu.moravian.sketchpad.ui.screens.GalleryScreen
import edu.moravian.sketchpad.ui.screens.ViewSketchScreen
import edu.moravian.sketchpad.ui.screens.SettingsScreen
import edu.moravian.sketchpad.data.SketchRepository
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSketchRepository = staticCompositionLocalOf<SketchRepository?> { null }

@Composable
fun App(sketchRepository: SketchRepository? = null) {
    val currentRoute = remember { mutableStateOf("home") }
    val currentSketchTitle = remember { mutableStateOf("") }
    val currentSketchId = remember { mutableStateOf(0L) }

    CompositionLocalProvider(LocalSketchRepository provides sketchRepository) {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                when (currentRoute.value) {
                    "home" -> HomeScreen(
                        onCreateNew = { currentRoute.value = "create" },
                        onViewGallery = { currentRoute.value = "gallery" },
                        onSettings = { currentRoute.value = "settings" }
                    )
                    "create" -> NewSketchScreen(
                        onConfirm = { title ->
                            currentSketchTitle.value = title
                            currentSketchId.value = 1L // Placeholder - will be set by save
                            currentRoute.value = "canvas"
                        },
                        onCancel = { currentRoute.value = "home" }
                    )
                    "canvas" -> CanvasEditorScreen(
                        title = currentSketchTitle.value,
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
}

@Preview
@Composable
fun AppPreview() {
    App()
}