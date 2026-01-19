package com.peto.droidmorning.exam.main.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamHistory
import com.peto.droidmorning.exam.main.model.ExamHistoryUiModel
import kotlin.time.Instant

class ExamHistoryPreviewProvider : PreviewParameterProvider<ExamHistory> {
    override val values: Sequence<ExamHistory>
        get() =
            sequenceOf(
                ExamHistory(
                    id = 1L,
                    exampleCount = 10,
                    categories = listOf(Category.Kotlin, Category.Android),
                    createdAt = Instant.fromEpochMilliseconds(0),
                ),
                ExamHistory(
                    id = 2L,
                    exampleCount = 20,
                    categories = listOf(Category.Compose),
                    createdAt = Instant.fromEpochMilliseconds(0),
                ),
                ExamHistory(
                    id = 3L,
                    exampleCount = 15,
                    categories = listOf(Category.Kotlin, Category.Coroutine, Category.OOP),
                    createdAt = Instant.fromEpochMilliseconds(0),
                ),
            )
}

class ExamHistoryUiModelPreviewProvider : PreviewParameterProvider<ExamHistoryUiModel> {
    override val values: Sequence<ExamHistoryUiModel>
        get() =
            sequenceOf(
                ExamHistoryUiModel(
                    id = 1L,
                    exampleCount = 10,
                    categories = listOf(Category.Kotlin, Category.Android),
                    formattedDate = "2024년 1월 20일",
                ),
                ExamHistoryUiModel(
                    id = 2L,
                    exampleCount = 20,
                    categories = listOf(Category.Compose),
                    formattedDate = "2024년 1월 18일",
                ),
                ExamHistoryUiModel(
                    id = 3L,
                    exampleCount = 15,
                    categories = listOf(Category.Kotlin, Category.Coroutine, Category.OOP),
                    formattedDate = "2024년 1월 15일",
                ),
            )
}
