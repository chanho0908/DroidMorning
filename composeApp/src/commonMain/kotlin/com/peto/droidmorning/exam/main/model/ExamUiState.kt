package com.peto.droidmorning.exam.main.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.category.Category

@Stable
data class ExamUiState(
    val selectedTab: ExamTab = ExamTab.CREATE,
    val createState: ExamCreateState = ExamCreateState(),
    val historyState: ExamHistoryState = ExamHistoryState(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val examToDelete: Long? = null,
) {
    fun selectTab(tab: ExamTab): ExamUiState = copy(selectedTab = tab)

    fun selectQuestionCount(count: Int): ExamUiState = copy(createState = createState.selectQuestionCount(count))

    fun toggleCategory(category: Category): ExamUiState = copy(createState = createState.toggleCategory(category))

    fun updateCategoryCounts(countMap: Map<Category, Long>): ExamUiState = copy(createState = createState.updateCategoryCounts(countMap))

    fun updateHistories(histories: List<ExamHistoryUiModel>): ExamUiState = copy(historyState = historyState.updateHistories(histories))

    fun showDeleteConfirmation(examId: Long): ExamUiState = copy(examToDelete = examId)

    fun hideDeleteConfirmation(): ExamUiState = copy(examToDelete = null)
}
