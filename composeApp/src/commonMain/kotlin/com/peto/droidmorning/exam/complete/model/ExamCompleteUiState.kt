package com.peto.droidmorning.exam.complete.model

import androidx.compose.runtime.Immutable
import com.peto.droidmorning.domain.model.exam.ExamDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class ExamCompleteUiState(
    val examDetails: ImmutableList<ExamDetail> = persistentListOf(),
) {
    fun updateExamDetails(examDetails: List<ExamDetail>): ExamCompleteUiState = copy(examDetails = examDetails.toImmutableList())
}
