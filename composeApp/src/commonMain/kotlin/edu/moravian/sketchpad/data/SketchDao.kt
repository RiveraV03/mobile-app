package edu.moravian.sketchpad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SketchDao {
    // Sketch operations
    @Insert
    suspend fun insertSketch(sketch: Sketch): Long

    @Query("SELECT * FROM sketches ORDER BY createdDate DESC")
    suspend fun getAllSketches(): List<Sketch>

    @Query("SELECT * FROM sketches WHERE id = :id")
    suspend fun getSketchById(id: Long): Sketch?

    @Delete
    suspend fun deleteSketch(sketch: Sketch)

    @Query("DELETE FROM sketches WHERE id = :id")
    suspend fun deleteSketchById(id: Long)

    // Stroke operations
    @Insert
    suspend fun insertStroke(stroke: StrokeData): Long

    @Query("SELECT * FROM stroke_data WHERE sketchId = :sketchId ORDER BY timestamp ASC")
    suspend fun getStrokesForSketch(sketchId: Long): List<StrokeData>

    @Delete
    suspend fun deleteStroke(stroke: StrokeData)

    @Query("DELETE FROM stroke_data WHERE sketchId = :sketchId")
    suspend fun deleteStrokesForSketch(sketchId: Long)
}