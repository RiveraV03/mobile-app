package edu.moravian.sketchpad

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.moravian.sketchpad.data.Sketch
import edu.moravian.sketchpad.data.SketchRepository
import edu.moravian.sketchpad.ui.screens.CanvasEditorScreen
import edu.moravian.sketchpad.ui.screens.GalleryScreen
import edu.moravian.sketchpad.ui.screens.HomeScreen
import edu.moravian.sketchpad.ui.screens.NewSketchScreen
import edu.moravian.sketchpad.ui.screens.SettingsScreen
import edu.moravian.sketchpad.ui.screens.ViewSketchScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val LocalSketchRepository = staticCompositionLocalOf<SketchRepository?> { null }

@Composable
fun App(sketchRepository: SketchRepository? = null) {
    val currentRoute = remember { mutableStateOf("home") }
    val currentSketchTitle = remember { mutableStateOf("") }
    val currentSketchId = remember { mutableStateOf(0L) }
    val sketches = remember { mutableStateOf(listOf<Sketch>()) }
    val isLoadingSketches = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentRoute.value) {
        if (currentRoute.value == "gallery" && sketchRepository != null) {
            isLoadingSketches.value = true
            try {
                val loadedSketches = withContext(Dispatchers.IO) {
                    sketchRepository.getAllSketches()
                }
                sketches.value = loadedSketches
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingSketches.value = false
            }
        }
    }

    CompositionLocalProvider(LocalSketchRepository provides sketchRepository) {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                when (currentRoute.value) {
                    "home" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        HomeScreen(
                            onCreateNew = { currentRoute.value = "create" },
                            onViewGallery = { currentRoute.value = "gallery" },
                            onSettings = { currentRoute.value = "settings" }
                        )
                    }
                    "create" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        NewSketchScreen(
                            onConfirm = { title ->
                                currentSketchTitle.value = title
                                currentRoute.value = "canvas"
                            },
                            onCancel = { currentRoute.value = "home" }
                        )
                    }
                    "canvas" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        CanvasEditorScreen(
                            title = currentSketchTitle.value,
                            onSave = { currentRoute.value = "home" },
                            onCancel = { currentRoute.value = "home" }
                        )
                    }
                    "gallery" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        GalleryScreen(
                            sketches = sketches.value,
                            isLoading = isLoadingSketches.value,
                            onSketchClicked = { sketchId ->
                                currentSketchId.value = sketchId
                                currentRoute.value = "view"
                            },
                            onSketchDeleted = { sketchId ->
                                scope.launch {
                                    if (sketchRepository != null) {
                                        try {
                                            withContext(Dispatchers.IO) {
                                                sketchRepository.deleteSketchById(sketchId)
                                                sketchRepository.deleteStrokesForSketch(sketchId)
                                            }
                                            val reloadedSketches = withContext(Dispatchers.IO) {
                                                sketchRepository.getAllSketches()
                                            }
                                            sketches.value = reloadedSketches
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                }
                            },
                            onBack = { currentRoute.value = "home" }
                        )
                    }
                    "view" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        ViewSketchScreen(
                            sketchId = currentSketchId.value,
                            onBack = { currentRoute.value = "gallery" }
                        )
                    }
                    "settings" -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        SettingsScreen(
                            preferencesRepo = null,
                            onBack = { currentRoute.value = "home" }
                        )
                    }
                    else -> AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        HomeScreen(
                            onCreateNew = { currentRoute.value = "create" },
                            onViewGallery = { currentRoute.value = "gallery" },
                            onSettings = { currentRoute.value = "settings" }
                        )
                    }
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