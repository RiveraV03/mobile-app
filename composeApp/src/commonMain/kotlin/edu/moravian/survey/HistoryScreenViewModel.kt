package edu.moravian.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.moravian.survey.data.SurveyResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the history screen.
 * Loads all survey results from the repository and exposes them as a StateFlow.
 */

class HistoryScreenViewModel(
    private val repository: SurveyRepository,
) : ViewModel() {
    private val _entries = MutableStateFlow(emptyList<SurveyResult>())
    val entries: StateFlow<List<SurveyResult>> = _entries.asStateFlow()

    private val _loaded = MutableStateFlow(false)
    val loaded: StateFlow<Boolean> = _loaded.asStateFlow()

    init {
        viewModelScope.launch {
            _entries.value = repository.getAll()
            _loaded.value = true
        }
    }
}
