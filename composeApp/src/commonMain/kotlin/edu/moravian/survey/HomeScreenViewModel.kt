package edu.moravian.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.moravian.survey.data.SurveyResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: SurveyRepository) : ViewModel() {
    private val _mostRecent = MutableStateFlow<SurveyResult?>(null)
    val mostRecent: StateFlow<SurveyResult?> = _mostRecent.asStateFlow()

    private val _loaded = MutableStateFlow(false)
    val loaded: StateFlow<Boolean> = _loaded.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _mostRecent.value = repository.getMostRecent()
            _loaded.value = true
        }
    }

    fun refresh() {
        _loaded.value = false
        loadData()
    }
}