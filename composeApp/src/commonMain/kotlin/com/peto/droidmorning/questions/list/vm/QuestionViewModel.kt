package com.peto.droidmorning.questions.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.repository.QuestionRepository
import com.peto.droidmorning.questions.list.model.QuestionUiEvent
import com.peto.droidmorning.questions.list.model.QuestionUiState
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
        _uiState.update {
            if (it.selectedCategories.contains(category)) {
                it.removeCategory(category)
            } else {
                it.addCategory(category)
            }
        }
        applyFilter()
    }

    fun onToggleCategoryFilters() {
        _uiState.update { it.toggleCategoryFilters() }
    }

    fun onSolvedFilterToggle() {
        _uiState.update {
            if (it.showSolvedOnly) {
                it.clearSolvedFilter()
            } else {
                it.applySolvedFilter()
            }
        }
        applyFilter()
    }

    fun onLikedFilterToggle() {
        _uiState.update {
            if (it.showLikedOnly) {
                it.clearLikedFilter()
            } else {
                it.applyLikedFilter()
            }
        }
        applyFilter()
    }

    private fun applyFilter() {
        filteringJob?.cancel()

        filteringJob =
            viewModelScope.launch {
                _uiState.update { it.filtering() }
                sendUiEvent(QuestionUiEvent.ScrollToTop)
                delay(500)
                _uiState.update { it.filterClear() }
            }
    }

    fun onLikeToggle(questionId: Long) {
        viewModelScope.launch {
            val currentQuestion = _uiState.value.filteredQuestions.find { it.id == questionId } ?: return@launch
            val isCurrentlyLiked = currentQuestion.isLiked

            _uiState.update { it.toggleQuestionLike(questionId) }

            questionRepository
                .toggleQuestionLike(questionId, isCurrentlyLiked)
                .onFailure {
                    _uiState.update { state -> state.toggleQuestionLike(questionId) }
                }
        }
    }

    fun updateQuestionFromDetail(
        questionId: Long,
        isLiked: Boolean,
        isSolved: Boolean,
    ) {
        _uiState.update {
            it.updateQuestion(questionId, isLiked, isSolved)
        }
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
                }
        }
    }

    private fun sendUiEvent(event: QuestionUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
