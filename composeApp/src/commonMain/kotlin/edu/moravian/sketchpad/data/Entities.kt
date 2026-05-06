package edu.moravian.sketchpad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single sketch saved by the user.
 */
@Entity(tableName = "sketches")
data class Sketch(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val createdDate: Long, // epoch milliseconds
    val thumbnail: String? = null, // Base64 encoded thumbnail or null
)

/**
 * Represents a single stroke drawn on a sketch canvas.
 * Multiple strokes make up a complete sketch.
 */
@Entity(tableName = "stroke_data")
data class StrokeData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sketchId: Long, // Foreign key to Sketch
    val pointsData: String, // Comma-separated or JSON string of points
    val color: Int, // ARGB color
    val brushSize: Float,
    val timestamp: Long = 0L,
)