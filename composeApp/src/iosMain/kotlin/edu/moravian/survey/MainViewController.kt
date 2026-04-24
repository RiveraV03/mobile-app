package edu.moravian.survey

import androidx.compose.ui.window.ComposeUIViewController
import androidx.room.Room
import edu.moravian.survey.data.SurveyDatabase

fun mainViewController() =
    ComposeUIViewController {
        val database =
            Room
                .databaseBuilder<SurveyDatabase>(
                    name = SurveyDatabase.DATABASE_NAME,
                ).build()
        val repository = SurveyRepository(database.surveyDao())
        App(repository)
    }
