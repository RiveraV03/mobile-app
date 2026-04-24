package edu.moravian.survey

import edu.moravian.survey.data.SurveyDao
import edu.moravian.survey.data.SurveyResult

/**
 * Repository that acts as the single source of truth for survey data.
 * All ViewModels and screens talk to this instead of the DAO directly.
 */
class SurveyRepository(
    private val dao: SurveyDao,
) {
    /** Insert a completed survey result, returns the new ID */
    suspend fun insert(result: SurveyResult): Long = dao.insert(result)

    /** Get all survey results, most recent first */
    suspend fun getAll(): List<SurveyResult> = dao.getAll()

    /** Get the most recent survey result, or null if none exist */
    suspend fun getMostRecent(): SurveyResult? = dao.getMostRecent()

    /** Get a specific survey result by ID, or null if not found */
    suspend fun getById(id: Long): SurveyResult? = dao.getById(id)
}
