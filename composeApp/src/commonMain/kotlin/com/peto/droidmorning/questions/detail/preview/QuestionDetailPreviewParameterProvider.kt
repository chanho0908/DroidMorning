package com.peto.droidmorning.questions.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.question.Question
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.model.QuestionDetailUiState
import kotlinx.collections.immutable.persistentListOf
import kotlin.time.Instant

class QuestionDetailPreviewParameterProvider : PreviewParameterProvider<QuestionDetailUiState> {
    override val values: Sequence<QuestionDetailUiState> =
        sequenceOf(
            QuestionDetailUiState(
                question =
                    Question(
                        id = 1,
                        title = "Kotlin의 val과 var의 차이점은 무엇인가요?",
                        category = Category.Kotlin,
                        sourceUrl = "",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = true,
                        isLiked = true,
                    ),
                currentAnswer =
                    AnswerUiModel.Current(
                        questionId = 1,
                        content = "val은 불변(immutable) 변수이고, var는 가변(mutable) 변수입니다. val은 초기화 후 값을 변경할 수 없지만, var는 언제든지 값을 변경할 수 있습니다.",
                        createdDate = "2024.01.01",
                        updatedDate = "2024.01.05",
                    ),
                historyAnswers = persistentListOf(),
                isLoading = false,
            ),
            QuestionDetailUiState(
                question =
                    Question(
                        id = 2,
                        title = "Coroutine의 Dispatcher 종류에 대해 설명해주세요.",
                        category = Category.Coroutine,
                        sourceUrl = "",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = false,
                    ),
                currentAnswer = null,
                historyAnswers = persistentListOf(),
                isLoading = false,
            ),
            QuestionDetailUiState(
                question =
                    Question(
                        id = 3,
                        title = "lateinit과 lazy의 차이점은 무엇인가요?",
                        category = Category.Kotlin,
                        sourceUrl = "",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = true,
                        isLiked = true,
                    ),
                currentAnswer =
                    AnswerUiModel.Current(
                        questionId = 3,
                        content = "lateinit은 var 프로퍼티에만 사용 가능하며, 나중에 초기화할 수 있습니다. lazy는 val 프로퍼티에 사용되며, 처음 접근할 때 초기화됩니다.",
                        createdDate = "2024.01.01",
                        updatedDate = "2024.02.01",
                    ),
                historyAnswers =
                    persistentListOf(
                        AnswerUiModel.History(
                            id = 1L,
                            questionId = 3,
                            content = "lateinit은 나중에 초기화할 수 있고, lazy는 처음 사용할 때 초기화됩니다.",
                            createdDate = "2024.01.01",
                        ),
                        AnswerUiModel.History(
                            id = 2L,
                            questionId = 3,
                            content = "lateinit은 var에만 사용 가능하고, lazy는 val에 사용됩니다.",
                            createdDate = "2024.01.15",
                        ),
                    ),
                isLoading = false,
            ),
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
            ),
        )
}
