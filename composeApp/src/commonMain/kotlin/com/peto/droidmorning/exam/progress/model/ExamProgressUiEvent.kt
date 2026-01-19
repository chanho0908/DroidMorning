package com.peto.droidmorning.exam.progress.model

sealed interface ExamProgressUiEvent {
    data class NavigateToComplete(
        val examId: Long,
    ) : ExamProgressUiEvent

    data object NavigateBack : ExamProgressUiEvent
}
