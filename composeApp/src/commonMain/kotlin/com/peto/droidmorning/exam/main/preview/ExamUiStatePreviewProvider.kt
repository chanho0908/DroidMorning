package com.peto.droidmorning.exam.main.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.main.model.ExamCreateState
import com.peto.droidmorning.exam.main.model.ExamHistoryState
import com.peto.droidmorning.exam.main.model.ExamHistoryUiModel
import com.peto.droidmorning.exam.main.model.ExamTab
import com.peto.droidmorning.exam.main.model.ExamUiState

class ExamUiStatePreviewProvider : PreviewParameterProvider<ExamUiState> {
    override val values: Sequence<ExamUiState>
        get() =
            sequenceOf(
                ExamUiState(
                    selectedTab = ExamTab.CREATE,
                    createState =
                        ExamCreateState(
                            selectedQuestionCount = 10,
                            selectedCategories = listOf(Category.Kotlin, Category.Android),
                            categoryCountMap =
                                mapOf(
                                    Category.Kotlin to 50L,
                                    Category.Android to 30L,
                                    Category.Compose to 20L,
                                ),
                        ),
                ),
                ExamUiState(
                    selectedTab = ExamTab.CREATE,
                    createState = ExamCreateState(),
                ),
                ExamUiState(
                    selectedTab = ExamTab.HISTORY,
                    historyState =
                        ExamHistoryState(
                            histories =
                                listOf(
                                    ExamHistoryUiModel(
                                        id = 1L,
                                        exampleCount = 10,
                                        categories = listOf(Category.Kotlin),
                                        formattedDate = "2024년 1월 20일",
                                    ),
                                    ExamHistoryUiModel(
                                        id = 2L,
                                        exampleCount = 15,
                                        categories = listOf(Category.Android, Category.Compose),
                                        formattedDate = "2024년 1월 18일",
                                    ),
                                ),
                        ),
                ),
                ExamUiState(
                    selectedTab = ExamTab.HISTORY,
                    historyState = ExamHistoryState(),
                ),
            )
}
