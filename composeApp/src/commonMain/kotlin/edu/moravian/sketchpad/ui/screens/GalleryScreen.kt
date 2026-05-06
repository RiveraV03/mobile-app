package edu.moravian.sketchpad.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.moravian.sketchpad.data.Sketch

@Composable
fun GalleryScreen(
    sketches: List<Sketch> = emptyList(),
    onSketchClicked: (Long) -> Unit = {},
    onSketchDeleted: (Long) -> Unit = {},
    onBack: () -> Unit = {},
    isLoading: Boolean = false
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Gallery", style = MaterialTheme.typography.headlineSmall)

        if (isLoading) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        } else if (sketches.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No sketches yet")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sketches) { sketch ->
                    Card(modifier = Modifier.fillMaxWidth().clickable { onSketchClicked(sketch.id) }) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Box(modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.LightGray), contentAlignment = Alignment.Center) {
                                Text("Sketch")
                            }
                            Text(sketch.title, style = MaterialTheme.typography.titleMedium)
                            Button(onClick = { onSketchDeleted(sketch.id) }, modifier = Modifier.fillMaxWidth()) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}