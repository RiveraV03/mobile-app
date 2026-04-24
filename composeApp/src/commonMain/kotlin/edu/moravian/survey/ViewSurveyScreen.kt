package edu.moravian.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.survey.data.toIntSet
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.loading
import surveytaker.composeapp.generated.resources.score

@Serializable
data class ViewSurveyScreenDest(
    val surveyId: Long,
)

@Composable
fun ViewSurveyScreen(
    surveyId: Long,
    repository: SurveyRepository,
) {
    var loading by remember { mutableStateOf(true) }
    var survey by remember { mutableStateOf(AMISOS_R_SURVEY) }
    var score by remember { mutableStateOf(0) }
    var timestamp by remember { mutableStateOf(0L) }

    LaunchedEffect(surveyId) {
        val result = repository.getById(surveyId)
        if (result != null) {
            score = result.score
            timestamp = result.timestamp

            // Pre-fill sounds answer
            var updatedSurvey = survey
            val soundsQuestion = updatedSurvey["sounds"] as? QuestionWithMultiOptions
            if (soundsQuestion != null) {
                updatedSurvey =
                    updatedSurvey.update(
                        soundsQuestion.copy(answer = result.soundsAnswer.toIntSet()),
                    )
            }

            // Pre-fill emotions answer
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

            survey = updatedSurvey
        }
        loading = false
    }

    if (loading) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator()
            Text(stringResource(Res.string.loading))
        }
        return
    }

    Column(
        modifier =
            Modifier
                .safeContentPadding()
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            stringResource(Res.string.score, score),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(8.dp),
        )
        Text(
            formatEpochMillis(timestamp),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        SurveyView(survey, false, null)
    }
}
