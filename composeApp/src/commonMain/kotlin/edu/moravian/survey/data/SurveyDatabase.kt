package edu.moravian.survey.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for the app. Contains one table for survey results.
 */
@Database(entities = [SurveyResult::class], version = 1)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object {
        const val DATABASE_NAME = "survey_database"
    }
}