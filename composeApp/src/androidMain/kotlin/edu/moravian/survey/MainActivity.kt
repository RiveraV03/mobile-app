package edu.moravian.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import edu.moravian.survey.data.SurveyDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            SurveyDatabase::class.java,
            SurveyDatabase.DATABASE_NAME,
        ).build()

        val repository = SurveyRepository(database.surveyDao())

        setContent {
            App(repository)
        }
    }
}