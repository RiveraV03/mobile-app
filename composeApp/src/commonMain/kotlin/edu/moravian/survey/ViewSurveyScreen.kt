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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.loading
import surveytaker.composeapp.generated.resources.score

/**
 * This file contains the screen for viewing a past survey result.
 */

@Serializable
data class ViewSurveyScreenDest(
    val surveyId: Long,
)

/**
 * View historical survey screen composable.
 *
 * Displays a past survey result in read-only mode with all original answers visible.
 * Shows the original score, submission date/time, and pre-filled Q1 & Q2 answers.
 * Q3-Q10 are shown blank (not persisted in database).
 *
 * @param surveyId The unique ID of the survey result to display
 * @param repository Repository for loading the survey result
 */
@Composable
fun ViewSurveyScreen(
    surveyId: Long,
    repository: SurveyRepository,
) {
    val viewModel = viewModel { ViewSurveyScreenViewModel(surveyId, repository) }
    val loading by viewModel.loading.collectAsState()
    val survey by viewModel.survey.collectAsState()
    val score by viewModel.score.collectAsState()
    val timestamp by viewModel.timestamp.collectAsState()

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
