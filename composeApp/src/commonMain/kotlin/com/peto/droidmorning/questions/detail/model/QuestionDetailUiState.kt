package com.peto.droidmorning.questions.detail.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.question.Question
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Instant

@Stable
data class QuestionDetailUiState(
    val question: Question,
    val currentAnswer: AnswerUiModel.Current?,
    val historyAnswers: ImmutableList<AnswerUiModel.History>,
    val isLoading: Boolean,
    val draftAnswer: String = "",
) {
    fun updateQuestion(question: Question): QuestionDetailUiState = copy(question = question)

    fun updateAnswers(
        currentAnswer: AnswerUiModel.Current?,
        historyAnswers: List<AnswerUiModel.History>,
    ): QuestionDetailUiState =
        copy(
            currentAnswer = currentAnswer,
            historyAnswers = historyAnswers.toImmutableList(),
            isLoading = false,
        )

    fun toggleFavorite(): QuestionDetailUiState {
        val currentQuestion = question
        return copy(question = currentQuestion.copy(isLiked = !currentQuestion.isLiked))
    }

    fun loading(isLoading: Boolean): QuestionDetailUiState = copy(isLoading = isLoading)

    fun updateDraftAnswer(content: String): QuestionDetailUiState = copy(draftAnswer = content)

    fun clearDraftAnswer(): QuestionDetailUiState = copy(draftAnswer = "")

    companion object {
        fun initial(): QuestionDetailUiState =
            QuestionDetailUiState(
                question =
                    Question(
                        id = 0,
                        title = "",
                        category = Category.Kotlin,
                        sourceUrl = "",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = false,
                    ),
                currentAnswer = null,
                historyAnswers = persistentListOf(),
                isLoading = true,
            )
    }
}
