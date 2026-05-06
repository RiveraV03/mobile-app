package edu.moravian.sketchpad.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for SketchPad. Contains tables for sketches and stroke data.
 */
@Database(entities = [Sketch::class, StrokeData::class], version = 1)
abstract class SketchPadDatabase : RoomDatabase() {
    abstract fun sketchDao(): SketchDao

    companion object {
        const val DATABASE_NAME = "sketchpad_database"
    }
}