package com.peto.droidmorning.exam.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.repository.ExamRepository
import com.peto.droidmorning.domain.repository.QuestionRepository
import com.peto.droidmorning.exam.main.model.ExamTab
import com.peto.droidmorning.exam.main.model.ExamUiEvent
import com.peto.droidmorning.exam.main.model.ExamUiState
import com.peto.droidmorning.exam.main.model.toUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamViewModel(
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExamUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ExamUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadCategoryCounts()
        loadExamHistory()
    }

    fun selectTab(tab: ExamTab) {
        _uiState.update { it.selectTab(tab) }
        if (tab == ExamTab.HISTORY) {
            loadExamHistory()
        }
    }

    fun selectQuestionCount(count: Int) {
        _uiState.update { it.selectQuestionCount(count) }
    }

    fun toggleCategory(category: Category) {
        _uiState.update { it.toggleCategory(category) }
    }

    private fun loadCategoryCounts() {
        viewModelScope.launch {
            questionRepository
                .fetchAllCategoryCount()
                .onSuccess { countMap ->
                    _uiState.update { examUiState ->
                        examUiState
                            .updateCategoryCounts(countMap)
                    }
                }
        }
    }

    private fun loadExamHistory() {
        viewModelScope.launch {
            examRepository
                .fetchExamHistory()
                .onSuccess { histories ->
                    _uiState.update { examUiState ->
                        examUiState.updateHistories(
                            histories.map { it.toUiModel() },
                        )
                    }
                }
        }
    }

    fun startExam() {
        viewModelScope.launch {
            val createState = _uiState.value.createState

            _uiEvent.send(
                ExamUiEvent.NavigateToExamProgress(
                    questionCount = createState.selectedQuestionCount,
                    categories = createState.selectedCategories,
                ),
            )
        }
    }

    fun openExamHistory(examId: Long) {
        viewModelScope.launch {
            _uiEvent.send(ExamUiEvent.NavigateToExamResult(examId))
        }
    }

    fun showDeleteConfirmation(examId: Long) {
        _uiState.update { it.showDeleteConfirmation(examId) }
    }

    fun hideDeleteConfirmation() {
        _uiState.update { it.hideDeleteConfirmation() }
    }

    fun deleteExam() {
        val examId = _uiState.value.examToDelete ?: return
        viewModelScope.launch {
            examRepository
                .deleteExam(examId)
                .onSuccess {
                    _uiState.update { it.hideDeleteConfirmation() }
                    _uiEvent.send(ExamUiEvent.ShowDeleteSuccessMessage)
                    loadExamHistory()
                }.onFailure {
                    _uiState.update {
                        it.hideDeleteConfirmation()
                    }
                }
        }
    }
}
