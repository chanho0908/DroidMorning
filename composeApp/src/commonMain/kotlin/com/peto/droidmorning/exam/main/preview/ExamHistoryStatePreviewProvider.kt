package com.peto.droidmorning.exam.main.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.main.model.ExamHistoryState
import com.peto.droidmorning.exam.main.model.ExamHistoryUiModel

class ExamHistoryStatePreviewProvider : PreviewParameterProvider<ExamHistoryState> {
    override val values: Sequence<ExamHistoryState>
        get() =
            sequenceOf(
                ExamHistoryState(),
                ExamHistoryState(
                    histories =
                        listOf(
                            ExamHistoryUiModel(
                                id = 1L,
                                exampleCount = 10,
                                categories = listOf(Category.Kotlin, Category.Android),
                                formattedDate = "2024년 1월 20일",
                            ),
                            ExamHistoryUiModel(
                                id = 2L,
                                exampleCount = 15,
                                categories = listOf(Category.Compose),
                                formattedDate = "2024년 1월 18일",
                            ),
                            ExamHistoryUiModel(
                                id = 3L,
                                exampleCount = 20,
                                categories = listOf(Category.Kotlin),
                                formattedDate = "2024년 1월 15일",
                            ),
                        ),
                ),
            )
}
