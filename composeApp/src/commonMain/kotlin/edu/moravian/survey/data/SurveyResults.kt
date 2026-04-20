package edu.moravian.survey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single completed survey submission stored in the database.
 *
 * @param id             Auto-generated unique ID for this result
 * @param score          The total score calculated from the answers
 * @param timestamp      The epoch milliseconds when the survey was submitted
 * @param soundsAnswer   The "sounds" question answer stored as comma-separated indices e.g. "0,2,5"
 * @param emotionsIndices The "emotions" question selected indices stored as comma-separated e.g. "0,1"
 * @param emotionsOther  The "emotions" question custom "Other" text if provided
 */
@Entity(tableName = "survey_results")
data class SurveyResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Int,
    val timestamp: Long,
    val soundsAnswer: String?,
    val emotionsIndices: String?,
    val emotionsOther: String?,
)

// --- Helper functions for converting answers to/from stored strings ---

/** Convert a Set<Int> to a storable comma-separated string: {0, 2, 5} -> "0,2,5" */
fun Set<Int>.toStoredString(): String = joinToString(",")

/** Convert a stored comma-separated string back to a Set<Int>: "0,2,5" -> {0, 2, 5} */
fun String?.toIntSet(): Set<Int> =
    if (isNullOrBlank()) emptySet()
    else split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()