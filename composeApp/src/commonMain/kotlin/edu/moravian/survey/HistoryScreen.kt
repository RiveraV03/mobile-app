package edu.moravian.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.moravian.survey.data.SurveyResult
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.date
import surveytaker.composeapp.generated.resources.history
import surveytaker.composeapp.generated.resources.no_history
import surveytaker.composeapp.generated.resources.score

/**
 * This file contains the history screen,
 * which shows a list of past survey results and allows the user to view details of each result
 */
@Serializable
data object HistoryScreen

@Composable
fun HistoryScreen(
    repository: SurveyRepository,
    onOpenSurvey: (Long) -> Unit,
) {
    val viewModel = viewModel { HistoryScreenViewModel(repository) }
    val entries by viewModel.entries.collectAsState()
    val loaded by viewModel.loaded.collectAsState()

    Column(
        modifier =
            Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(Res.string.history), style = MaterialTheme.typography.headlineSmall)

        if (loaded && entries.isEmpty()) {
            Text(stringResource(Res.string.no_history))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(entries, key = { it.id }) { result ->
                Card(modifier = Modifier.fillMaxWidth().clickable { onOpenSurvey(result.id) }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            stringResource(Res.string.score, result.score),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            stringResource(Res.string.date, formatEpochMillis(result.timestamp)),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}
