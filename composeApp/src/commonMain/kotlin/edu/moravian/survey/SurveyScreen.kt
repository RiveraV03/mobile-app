package edu.moravian.survey

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.moravian.survey.data.SurveyRepository
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.answer_all
import surveytaker.composeapp.generated.resources.submit

@Serializable
data object SurveyScreen

@Composable
fun SurveyScreen(
    repository: SurveyRepository,
    onCompleted: () -> Unit,
) {
    val viewModel = viewModel { SurveyViewModel(repository) }
    val survey by viewModel.survey.collectAsState()
    val submitAttempted by viewModel.submitAttempted.collectAsState()

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        SurveyView(
            survey = survey,
            showErrors = submitAttempted,
            onAnswer = { viewModel.onAnswer(it) },
        )

        if (submitAttempted && survey.questions.hasErrors) {
            Text(
                stringResource(Res.string.answer_all),
                color = MaterialTheme.colorScheme.error,
            )
        }

        Button(
            onClick = { viewModel.onSubmit(onCompleted) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Text(stringResource(Res.string.submit))
        }
    }
}

@Composable
fun ColumnScope.SurveyView(
    survey: Survey,
    showErrors: Boolean = false,
    onAnswer: ((Survey) -> Unit)? = null,
) {
    survey.Render(
        Modifier
            .weight(1f)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.shapes.medium,
            ).padding(10.dp)
            .fillMaxWidth(),
        showErrors,
        onAnswer,
    )
}