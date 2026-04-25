package edu.moravian.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.moravian.survey.data.SurveyResult
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.last_completed
import surveytaker.composeapp.generated.resources.last_score
import surveytaker.composeapp.generated.resources.no_survey_results_yet
import surveytaker.composeapp.generated.resources.take_survey
import surveytaker.composeapp.generated.resources.view_history

/**
 * This file contains the home screen,
 * which shows the most recent survey result and options to take a new survey or view history.
 */
@Serializable
data object HomeScreen

/**
 * Home screen composable.
 *
 * Displays the user's most recent survey result, reminder status, and action buttons.
 * Refreshes data every time the screen becomes visible to ensure it's always current.
 *
 * @param repository Repository for loading survey data
 * @param onTakeSurvey Callback when "Take Survey" button is tapped
 * @param onOpenHistory Callback when "View History" button is tapped
 */
@Composable
fun HomeScreen(
    repository: SurveyRepository,
    onTakeSurvey: () -> Unit,
    onOpenHistory: () -> Unit,
) {
    val viewModel = viewModel { HomeScreenViewModel(repository) }
    val mostRecent by viewModel.mostRecent.collectAsState()
    val loaded by viewModel.loaded.collectAsState()

    // Refresh data every time this screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Column(
        modifier =
            Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (loaded) {
            StatusText(mostRecent)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onTakeSurvey) { Text(stringResource(Res.string.take_survey)) }
        TextButton(onClick = onOpenHistory) { Text(stringResource(Res.string.view_history)) }
    }
}

/**
 * Displays status information about the most recent survey.
 *
 * Shows the score, completion date/time, and reminder message (if applicable).
 * If no surveys exist, displays "You have not taken the survey yet."
 *
 * @param mostRecent The most recent survey result, or null if none exist
 */
@Composable
private fun StatusText(mostRecent: SurveyResult?) {
    val now = currentTimeMillis()

    if (mostRecent == null) {
        Text(stringResource(Res.string.no_survey_results_yet))
        return
    }

    Text(stringResource(Res.string.last_score, mostRecent.score))
    Text(stringResource(Res.string.last_completed, formatEpochMillis(mostRecent.timestamp)))

    val reminder = reminderMessage(now, mostRecent.timestamp)
    if (reminder != null) {
        Text(
            stringResource(reminder),
            color = MaterialTheme.colorScheme.error,
        )
    }
}
