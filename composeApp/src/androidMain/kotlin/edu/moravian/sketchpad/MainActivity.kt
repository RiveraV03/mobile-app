package edu.moravian.sketchpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import edu.moravian.sketchpad.data.SketchPadDatabase
import edu.moravian.sketchpad.data.SketchRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database
        val database = Room.databaseBuilder(
            applicationContext,
            SketchPadDatabase::class.java,
            "sketchpad_database"
        ).build()

        val repository = SketchRepository(database.sketchDao())

        setContent {
            App(sketchRepository = repository)
        }
    }
}