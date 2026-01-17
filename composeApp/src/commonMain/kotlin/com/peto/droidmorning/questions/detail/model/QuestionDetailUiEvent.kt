package com.peto.droidmorning.questions.detail.model

sealed interface QuestionDetailUiEvent {
    data class NavigateBack(
        val result: QuestionUpdateResult,
    ) : QuestionDetailUiEvent
}
