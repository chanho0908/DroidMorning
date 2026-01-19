package com.peto.droidmorning.domain.model.exam

import com.peto.droidmorning.domain.model.category.Category

data class ExamQuestion(
    val questionId: Long,
    val content: String,
    val category: Category,
)
