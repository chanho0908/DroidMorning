package com.peto.droidmorning.exam.detail.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.exam.ExamDetail

@Stable
data class ExamDetailUiState(
    val examQuestions: List<ExamDetail> = emptyList(),
) {
    val examQuestionCount: Int
        get() = examQuestions.size

    fun updateExamQuestions(questions: List<ExamDetail>): ExamDetailUiState = copy(examQuestions = questions)
}
