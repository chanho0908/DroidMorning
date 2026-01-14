package com.peto.droidmorning.question.vm

sealed interface QuestionUiEvent {
    data class NavigateToQuestionDetail(
        val questionId: Long,
    ) : QuestionUiEvent

    data object ShowError : QuestionUiEvent

    data object ScrollToTop : QuestionUiEvent
}
