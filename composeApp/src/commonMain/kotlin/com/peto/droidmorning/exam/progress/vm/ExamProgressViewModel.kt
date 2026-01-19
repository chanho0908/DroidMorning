package com.peto.droidmorning.exam.progress.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.repository.ExamRepository
import com.peto.droidmorning.domain.repository.QuestionRepository
import com.peto.droidmorning.exam.progress.model.ExamProgressUiEvent
import com.peto.droidmorning.exam.progress.model.ExamProgressUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamProgressViewModel(
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExamProgressUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ExamProgressUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun loadExamQuestions(
        questionCount: Int,
        categories: List<Category>,
    ) {
        viewModelScope.launch {
            _uiState.update { it.loading(true) }

            questionRepository
                .fetchExamQuestions(
                    questionCount = questionCount,
                    categories = categories,
                ).onSuccess { examQuestions ->
                    _uiState.update { it.examLoaded(examQuestions, categories) }
                }.onFailure {
                    _uiState.update { it.loading(false) }
                }
        }
    }

    fun onAnswerChanged(
        questionId: Long,
        answer: String,
    ) {
        _uiState.update { it.updateAnswer(questionId, answer) }
    }

    fun previousQuestion() {
        _uiState.update { it.moveToPreviousQuestion() }
    }

    fun nextQuestion() {
        _uiState.update { it.moveToNextQuestion() }
    }

    fun submitExam() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.questions.isEmpty()) return@launch

            examRepository
                .submitExam(state.exams, categories = state.categories)
                .onSuccess {
                    sendEvent(ExamProgressUiEvent.NavigateToComplete(it))
                }
        }
    }

    fun cancelExam() {
        viewModelScope.launch {
            sendEvent(ExamProgressUiEvent.NavigateBack)
        }
    }

    private fun sendEvent(event: ExamProgressUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
