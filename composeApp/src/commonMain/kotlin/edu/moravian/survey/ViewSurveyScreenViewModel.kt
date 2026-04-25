package edu.moravian.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.moravian.survey.data.toIntSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the view survey screen.
 * Loads a specific survey result by ID from the repository and pre-fills the survey if available.
 * Exposes the current survey state, score, timestamp, and loading status as StateFlows.
 */

class ViewSurveyScreenViewModel(
    private val surveyId: Long,
    private val repository: SurveyRepository,
) : ViewModel() {
    private val _survey = MutableStateFlow(AMISOS_R_SURVEY)
    val survey: StateFlow<Survey> = _survey.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _timestamp = MutableStateFlow(0L)
    val timestamp: StateFlow<Long> = _timestamp.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getById(surveyId)
            if (result != null) {
                _score.value = result.score
                _timestamp.value = result.timestamp

                // Pre-fill sounds answer
                var updatedSurvey = _survey.value
                val soundsQuestion = updatedSurvey["sounds"] as? QuestionWithMultiOptions
                if (soundsQuestion != null) {
                    updatedSurvey =
                        updatedSurvey.update(
                            soundsQuestion.copy(answer = result.soundsAnswer.toIntSet()),
                        )
                }

                // Pre-fill emotions answer
                val emotionsQuestion =
                    updatedSurvey["emotions"] as? QuestionWithMultiOptionsAndOther
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

                _survey.value = updatedSurvey
            }
            _loading.value = false
        }
    }
}
