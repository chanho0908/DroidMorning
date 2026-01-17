package com.peto.droidmorning.questions.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.AnswerRepository
import com.peto.droidmorning.domain.repository.QuestionRepository
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.model.QuestionDetailUiEvent
import com.peto.droidmorning.questions.detail.model.QuestionDetailUiState
import com.peto.droidmorning.questions.detail.model.QuestionUpdateResult
import com.peto.droidmorning.questions.detail.model.toUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val questionId: Long,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionDetailUiState.initial())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<QuestionDetailUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadQuestionDetail()
    }

    private fun loadQuestionDetail() {
        viewModelScope.launch {
            _uiState.update { it.loading(true) }

            questionRepository
                .fetchQuestions()
                .onSuccess { questions ->
                    val question = questions.toList().find { it.id == questionId }
                    if (question != null) {
                        _uiState.update { it.updateQuestion(question) }
                        loadAnswers()
                    } else {
                        _uiState.update { it.loading(false) }
                    }
                }.onFailure {
                    _uiState.update { it.loading(false) }
                }
        }
    }

    private suspend fun loadAnswers() {
        val currentResult = answerRepository.fetchCurrentAnswer(questionId)
        val currentAnswer = currentResult.getOrNull()

        val historyResult = answerRepository.fetchAnswerHistory(questionId)
        val historyAnswers = historyResult.getOrElse { emptyList() }

        val currentAnswerUi: AnswerUiModel.Current? =
            currentAnswer?.toUiModel() as? AnswerUiModel.Current

        val historyAnswersUi: List<AnswerUiModel.History> =
            historyAnswers.mapNotNull { it.toUiModel() as? AnswerUiModel.History }

        _uiState.update { it.updateAnswers(currentAnswerUi, historyAnswersUi) }
    }

    fun onDraftAnswerChange(content: String) {
        _uiState.update { it.updateDraftAnswer(content) }
    }

    fun onAddAnswer(content: String) {
        if (content.trim().isEmpty()) return

        viewModelScope.launch {
            answerRepository
                .saveAnswer(questionId, content)
                .onSuccess {
                    _uiState.update { it.clearDraftAnswer() }
                    loadAnswers()
                    updateQuestionSolvedStatus(true)
                }
        }
    }

    fun onUpdateAnswer(
        answer: AnswerUiModel.Current,
        content: String,
    ) {
        if (content.trim().isEmpty()) return

        viewModelScope.launch {
            answerRepository
                .updateAnswer(answer.questionId, content)
                .onSuccess {
                    loadAnswers()
                }
        }
    }

    fun onDeleteAnswer(answer: AnswerUiModel) {
        viewModelScope.launch {
            when (answer) {
                is AnswerUiModel.History -> {
                    // 히스토리 답변 삭제
                    answerRepository
                        .deleteAnswerHistory(answer.id)
                        .onSuccess {
                            loadAnswers()
                        }
                }

                is AnswerUiModel.Current -> {
                    // 현재 답변 삭제
                    answerRepository
                        .deleteCurrentAnswer(questionId)
                        .onSuccess {
                            // loadAnswers()가 완료될 때까지 기다린 후 상태 확인
                            viewModelScope.launch {
                                loadAnswers()

                                // loadAnswers() 완료 후 히스토리에서 복원된 답변이 있는지 확인
                                val hasAnswerAfterDelete = _uiState.value.currentAnswer != null
                                if (!hasAnswerAfterDelete) {
                                    // 모든 답변이 삭제되었으면 미해결 상태로 변경
                                    updateQuestionSolvedStatus(false)
                                }
                            }
                        }
                }
            }
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch {
            val currentQuestion = _uiState.value.question
            val isCurrentlyLiked = currentQuestion.isLiked

            _uiState.update { it.toggleFavorite() }

            questionRepository
                .toggleQuestionLike(questionId, isCurrentlyLiked)
                .onFailure {
                    _uiState.update { it.toggleFavorite() }
                }
        }
    }

    private fun updateQuestionSolvedStatus(isSolved: Boolean) {
        viewModelScope.launch {
            val currentQuestion = _uiState.value.question
            _uiState.update {
                it.updateQuestion(currentQuestion.copy(isSolved = isSolved))
            }
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            val result =
                _uiState.value.question.run {
                    QuestionUpdateResult(isLiked, isSolved)
                }
            _uiEvent.send(QuestionDetailUiEvent.NavigateBack(result))
        }
    }
}
