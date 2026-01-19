package com.peto.droidmorning.exam.main.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.common.util.DateFormatter
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamHistory

@Stable
data class ExamHistoryUiModel(
    val id: Long,
    val exampleCount: Int,
    val categories: List<Category>,
    val formattedDate: String,
)

fun ExamHistory.toUiModel(): ExamHistoryUiModel =
    ExamHistoryUiModel(
        id = id,
        exampleCount = exampleCount,
        categories = categories,
        formattedDate = DateFormatter.formatDate(createdAt),
    )
