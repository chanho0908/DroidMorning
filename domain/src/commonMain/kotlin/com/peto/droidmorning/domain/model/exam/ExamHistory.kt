package com.peto.droidmorning.domain.model.exam

import com.peto.droidmorning.domain.model.category.Category
import kotlin.time.Instant

data class ExamHistory(
    val id: Long,
    val exampleCount: Int,
    val categories: List<Category>,
    val createdAt: Instant,
)
