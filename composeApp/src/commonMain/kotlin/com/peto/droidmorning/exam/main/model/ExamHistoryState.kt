package com.peto.droidmorning.exam.main.model

import androidx.compose.runtime.Stable

@Stable
data class ExamHistoryState(
    val histories: List<ExamHistoryUiModel> = emptyList(),
) {
    fun updateHistories(histories: List<ExamHistoryUiModel>): ExamHistoryState = copy(histories = histories)
}
