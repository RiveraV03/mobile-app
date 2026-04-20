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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.survey.data.SurveyRepository
import edu.moravian.survey.data.SurveyResult
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.last_completed
import surveytaker.composeapp.generated.resources.last_score
import surveytaker.composeapp.generated.resources.no_survey_results_yet
import surveytaker.composeapp.generated.resources.take_survey
import surveytaker.composeapp.generated.resources.view_history

@Serializable
data object HomeScreen

@Composable
fun HomeScreen(
    repository: SurveyRepository,
    onTakeSurvey: () -> Unit,
    onOpenHistory: () -> Unit,
) {
    var mostRecent by remember { mutableStateOf<SurveyResult?>(null) }
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mostRecent = repository.getMostRecent()
        loaded = true
    }

    Column(
        modifier = Modifier
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