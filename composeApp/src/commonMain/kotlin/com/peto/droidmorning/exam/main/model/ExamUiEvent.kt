package com.peto.droidmorning.exam.main.model

import com.peto.droidmorning.domain.model.category.Category

sealed interface ExamUiEvent {
    data class NavigateToExamProgress(
        val questionCount: Int,
        val categories: List<Category>,
    ) : ExamUiEvent

    data class NavigateToExamResult(
        val examId: Long,
    ) : ExamUiEvent

    data object ShowDeleteSuccessMessage : ExamUiEvent
}
