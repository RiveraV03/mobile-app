package edu.moravian.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.moravian.survey.data.SurveyResult
import edu.moravian.survey.data.toIntSet
import edu.moravian.survey.data.toStoredString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SurveyViewModel(
    private val repository: SurveyRepository,
) : ViewModel() {
    private val _survey = MutableStateFlow(AMISOS_R_SURVEY)
    val survey: StateFlow<Survey> = _survey.asStateFlow()

    private val _submitAttempted = MutableStateFlow(false)
    val submitAttempted: StateFlow<Boolean> = _submitAttempted.asStateFlow()

    init {
        viewModelScope.launch {
            val recent = repository.getMostRecent() ?: return@launch

            var updatedSurvey = _survey.value

            val soundsQuestion = updatedSurvey["sounds"] as? QuestionWithMultiOptions
            if (soundsQuestion != null) {
                updatedSurvey =
                    updatedSurvey.update(
                        soundsQuestion.copy(answer = recent.soundsAnswer.toIntSet()),
                    )
            }

            val emotionsQuestion = updatedSurvey["emotions"] as? QuestionWithMultiOptionsAndOther
            if (emotionsQuestion != null) {
                updatedSurvey =
                    updatedSurvey.update(
                        emotionsQuestion.copy(
                            answer =
                                Pair(
                                    recent.emotionsIndices.toIntSet(),
                                    recent.emotionsOther ?: "",
                                ),
                        ),
                    )
            }

            _survey.value = updatedSurvey
        }
    }

    fun onAnswer(updatedSurvey: Survey) {
        _survey.value = updatedSurvey
    }

    fun onSubmit(onCompleted: () -> Unit) {
        _submitAttempted.value = true

        val questions = _survey.value.questions
        if (questions.hasErrors) return

        viewModelScope.launch {
            val soundsAnswer =
                (_survey.value["sounds"] as? QuestionWithMultiOptions)
                    ?.answer
                    ?.toStoredString()

            val emotionsQuestion = _survey.value["emotions"] as? QuestionWithMultiOptionsAndOther
            val emotionsIndices = emotionsQuestion?.answer?.first?.toStoredString()
            val emotionsOther = emotionsQuestion?.answer?.second

            repository.insert(
                SurveyResult(
                    score = questions.score,
                    timestamp = currentTimeMillis(),
                    soundsAnswer = soundsAnswer,
                    emotionsIndices = emotionsIndices,
                    emotionsOther = emotionsOther,
                ),
            )
            onCompleted()
        }
    }
}
