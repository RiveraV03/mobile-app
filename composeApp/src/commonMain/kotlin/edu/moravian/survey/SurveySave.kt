package edu.moravian.survey

import edu.moravian.survey.data.SurveyResult
import edu.moravian.survey.data.toIntSet
import edu.moravian.survey.data.toStoredString

/**
 * Saves the current survey result to the repository. This should be called when the user completes
 * the survey. Extracts answer data from questions 1 and 2 (sounds and emotions), calculates the
 * total score, and persists to the database.
 */
suspend fun SurveyQuestions.save(repository: SurveyRepository) {
    val soundsAnswer =
        (this.firstOrNull { it.id == "sounds" } as? QuestionWithMultiOptions)
            ?.answer
            ?.toStoredString()

    val emotionsQuestion = this.firstOrNull { it.id == "emotions" } as? QuestionWithMultiOptionsAndOther
    val emotionsIndices = emotionsQuestion?.answer?.first?.toStoredString()
    val emotionsOther = emotionsQuestion?.answer?.second

    repository.insert(
        SurveyResult(
            score = this.score,
            timestamp = currentTimeMillis(),
            soundsAnswer = soundsAnswer,
            emotionsIndices = emotionsIndices,
            emotionsOther = emotionsOther,
        ),
    )
}

/**
 * Loads the survey result with the given ID from the repository and maps it back to a Survey
 * by populating the "sounds" and "emotions" questions with their stored answers.
 */
suspend fun Survey.load(
    surveyId: Long,
    repository: SurveyRepository,
): Survey {
    val result = repository.getById(surveyId) ?: return this

    var updatedSurvey = this

    val soundsQuestion = updatedSurvey["sounds"] as? QuestionWithMultiOptions
    if (soundsQuestion != null) {
        updatedSurvey =
            updatedSurvey.update(
                soundsQuestion.copy(answer = result.soundsAnswer.toIntSet()),
            )
    }

    val emotionsQuestion = updatedSurvey["emotions"] as? QuestionWithMultiOptionsAndOther
    if (emotionsQuestion != null) {
        updatedSurvey =
            updatedSurvey.update(
                emotionsQuestion.copy(
                    answer =
                        Pair(
                            result.emotionsIndices.toIntSet(),
                            result.emotionsOther ?: "",
                        ),
                ),
            )
    }

    return updatedSurvey
}
