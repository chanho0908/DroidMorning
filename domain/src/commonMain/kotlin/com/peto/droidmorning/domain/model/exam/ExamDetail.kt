package com.peto.droidmorning.domain.model.exam

import com.peto.droidmorning.domain.model.category.Category

data class ExamDetail(
    val examItemId: Long,
    val examId: Long,
    val questionId: Long,
    val userAnswer: String,
    val questionTitle: String,
    val questionCategory: Category,
    val questionSourceUrl: String,
)
