package com.peto.droidmorning.exam.progress.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamQuestion
import com.peto.droidmorning.domain.model.exam.Exams

@Stable
data class ExamProgressUiState(
    val questions: List<ExamQuestion> = emptyList(),
    val categories: List<Category> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val exams: Exams = Exams(),
    val isLoading: Boolean = false,
) {
    val currentQuestion: ExamQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    val isFirstQuestion: Boolean
        get() = currentQuestionIndex == 0

    val isLastQuestion: Boolean
        get() = currentQuestionIndex == questions.size - 1

    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentQuestionIndex + 1).toFloat() / questions.size

    val currentAnswer: String?
        get() = currentQuestion?.let { exams[it.questionId] }

    val hasCurrentAnswer: Boolean
        get() = !currentAnswer.isNullOrBlank()

    val canGoNext: Boolean
        get() = hasCurrentAnswer

    val canSubmit: Boolean
        get() {
            if (questions.isEmpty()) return false
            return questions.all { question ->
                exams[question.questionId].isNotBlank()
            }
        }

    fun loading(isLoading: Boolean): ExamProgressUiState = copy(isLoading = isLoading)

    fun examLoaded(
        examQuestions: List<ExamQuestion>,
        categories: List<Category>,
    ): ExamProgressUiState = copy(questions = examQuestions, categories = categories, isLoading = false)

    fun updateAnswer(
        questionId: Long,
        answer: String,
    ): ExamProgressUiState = copy(exams = exams.updateAnswer(questionId, answer))

    fun moveToPreviousQuestion(): ExamProgressUiState =
        if (currentQuestionIndex > 0) {
            copy(currentQuestionIndex = currentQuestionIndex - 1)
        } else {
            this
        }

    fun moveToNextQuestion(): ExamProgressUiState =
        if (currentQuestionIndex < questions.size - 1) {
            copy(currentQuestionIndex = currentQuestionIndex + 1)
        } else {
            this
        }
}
