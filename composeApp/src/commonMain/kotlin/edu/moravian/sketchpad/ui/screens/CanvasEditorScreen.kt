package edu.moravian.sketchpad.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import edu.moravian.sketchpad.LocalSketchRepository
import edu.moravian.sketchpad.data.Sketch
import edu.moravian.sketchpad.data.StrokeData
import kotlinx.coroutines.launch

data class DrawPoint(val x: Float, val y: Float)

data class DrawStroke(
    val points: List<DrawPoint>,
    val color: Color,
    val brushSize: Float,
)

@Composable
fun CanvasEditorScreen(
    title: String = "Drawing",
    onSave: (List<DrawStroke>) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var brushSize by remember { mutableStateOf(5f) }
    var brushColor by remember { mutableStateOf(Color.Black) }
    var strokes by remember { mutableStateOf(listOf<DrawStroke>()) }
    var currentStroke by remember { mutableStateOf(listOf<DrawPoint>()) }
    val scope = rememberCoroutineScope()
    val repository = LocalSketchRepository.current

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Text(
            title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        // Canvas area with drawing
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentStroke = listOf(DrawPoint(offset.x, offset.y))
                        },
                        onDrag = { change, _ ->
                            currentStroke = currentStroke + DrawPoint(change.position.x, change.position.y)
                        },
                        onDragEnd = {
                            if (currentStroke.isNotEmpty()) {
                                strokes = strokes + DrawStroke(currentStroke, brushColor, brushSize)
                                currentStroke = emptyList()
                            }
                        }
                    )
                }
        ) {
            drawRect(Color.White)
            // Draw completed strokes
            for (stroke in strokes) {
                drawStroke(stroke)
            }
            // Draw current stroke
            if (currentStroke.isNotEmpty()) {
                drawCurrentStroke(currentStroke, brushColor, brushSize)
            }
        }

        // Color picker (simplified: color swatches)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val colors = listOf(Color.Black, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta)
            for (color in colors) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(color)
                        .clickable { brushColor = color }
                )
            }
        }

        // Brush size slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Size: ${brushSize.toInt()}px")
            Slider(
                value = brushSize,
                onValueChange = { brushSize = it },
                valueRange = 1f..20f,
                modifier = Modifier.weight(1f)
            )
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { strokes = if (strokes.isNotEmpty()) strokes.dropLast(1) else emptyList() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Undo")
            }
            Button(onClick = onCancel, modifier = Modifier.weight(1f)) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    scope.launch {
                        if (repository != null && strokes.isNotEmpty()) {
                            try {
                                val sketch = Sketch(
                                    title = title,
                                    createdDate = 0L
                                )
                                val sketchId = repository.insertSketch(sketch)

                                for ((index, stroke) in strokes.withIndex()) {
                                    val strokeData = StrokeData(
                                        sketchId = sketchId,
                                        pointsData = stroke.points.joinToString(";") { "${it.x},${it.y}" },
                                        color = stroke.color.value.toLong().toInt(),
                                        brushSize = stroke.brushSize,
                                        timestamp = 0L + index
                                    )
                                    repository.insertStroke(strokeData)
                                }
                                onSave(strokes)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            onSave(strokes)
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}

private fun DrawScope.drawStroke(stroke: DrawStroke) {
    if (stroke.points.size < 2) return
    val path = Path()
    path.moveTo(stroke.points[0].x, stroke.points[0].y)
    for (i in 1 until stroke.points.size) {
        path.lineTo(stroke.points[i].x, stroke.points[i].y)
    }
    drawPath(path, color = stroke.color, style = Stroke(width = stroke.brushSize))
}

private fun DrawScope.drawCurrentStroke(points: List<DrawPoint>, color: Color, brushSize: Float) {
    if (points.size < 2) return
    val path = Path()
    path.moveTo(points[0].x, points[0].y)
    for (i in 1 until points.size) {
        path.lineTo(points[i].x, points[i].y)
    }
    drawPath(path, color = color, style = Stroke(width = brushSize))
}