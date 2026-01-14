package com.peto.droidmorning.question.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.repository.QuestionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val questionRepository: QuestionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<QuestionUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private var filteringJob: Job? = null

    init {
        loadQuestions()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.updateSearchQuery(query) }
        applyFilter()
    }

    fun onCategoryToggle(category: Category) {
        _uiState.update { it.toggleCategory(category) }
        applyFilter()
    }

    fun onToggleCategoryFilters() {
        _uiState.update { it.toggleCategoryFilters() }
    }

    fun onSolvedFilterToggle() {
        _uiState.update { it.toggleSolvedFilter() }
        applyFilter()
    }

    fun onFavoritesFilterToggle() {
        _uiState.update { it.toggleFavoritesFilter() }
        applyFilter()
    }

    private fun applyFilter() {
        filteringJob?.cancel()

        filteringJob =
            viewModelScope.launch {
                _uiState.update { it.filtering() }
                sendUiEvent(QuestionUiEvent.ScrollToTop)
                delay(500)
                _uiState.update { it.filterCompleted() }
            }
    }

    fun onFavoriteToggle(questionId: Long) {
        // TODO: Implement favorite toggle
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.update { it.loading(true) }
            questionRepository
                .fetchQuestions()
                .onSuccess { questions ->
                    _uiState.update { it.updateQuestions(questions) }
                }.onFailure {
                    _uiState.update { it.loading(false) }
                    sendUiEvent(QuestionUiEvent.ShowError)
                }
        }
    }

    private fun sendUiEvent(event: QuestionUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
