package edu.moravian.survey.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SurveyDao {
    /** Insert a new survey result, returns the new row's auto-generated ID */
    @Insert
    suspend fun insert(result: SurveyResult): Long

    /** Get all results ordered most recent first (for the history screen) */
    @Query("SELECT * FROM survey_results ORDER BY timestamp DESC")
    suspend fun getAll(): List<SurveyResult>

    /** Get only the most recent result (for home screen info and pre-filling questions 1 & 2) */
    @Query("SELECT * FROM survey_results ORDER BY timestamp DESC LIMIT 1")
    suspend fun getMostRecent(): SurveyResult?

    /** Get a single result by its ID (for the view survey screen) */
    @Query("SELECT * FROM survey_results WHERE id = :id")
    suspend fun getById(id: Long): SurveyResult?
}
