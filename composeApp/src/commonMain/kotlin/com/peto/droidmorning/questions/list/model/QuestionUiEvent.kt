package com.peto.droidmorning.questions.list.model

sealed interface QuestionUiEvent {
    data class NavigateToQuestionDetail(
        val questionId: Long,
    ) : QuestionUiEvent

    data object ScrollToTop : QuestionUiEvent
}
