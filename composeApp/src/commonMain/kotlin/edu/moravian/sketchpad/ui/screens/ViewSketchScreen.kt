package edu.moravian.sketchpad.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import edu.moravian.sketchpad.LocalSketchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

@Composable
fun ViewSketchScreen(
    sketchId: Long = 0L,
    onBack: () -> Unit = {}
) {
    val repository = LocalSketchRepository.current
    val strokePoints = remember { mutableStateOf(listOf<Triple<Float, Float, String>>()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(sketchId) {
        if (repository != null && sketchId > 0) {
            try {
                val strokes = withContext(Dispatchers.IO) {
                    repository.getStrokesForSketch(sketchId)
                }
                val points = mutableListOf<Triple<Float, Float, String>>()
                for (stroke in strokes) {
                    val parsedPoints = stroke.pointsData.split(";").map {
                        val (x, y) = it.split(",")
                        Triple(x.toFloat(), y.toFloat(), stroke.color.toString())
                    }
                    points.addAll(parsedPoints)
                }
                strokePoints.value = points
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.value = false
        } else {
            isLoading.value = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Saved Sketch", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))

        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            drawRect(Color.White)
            if (strokePoints.value.isNotEmpty()) {
                val path = Path()
                var isFirstPoint = true
                for ((x, y, _) in strokePoints.value) {
                    if (isFirstPoint) {
                        path.moveTo(x, y)
                        isFirstPoint = false
                    } else {
                        path.lineTo(x, y)
                    }
                }
                drawPath(path, color = Color.Black, style = Stroke(width = 5f))
            } else {
                drawRect(Color.LightGray)
            }
        }

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Back to Gallery")
        }
    }
}