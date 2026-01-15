package com.peto.droidmorning.domain.model

import kotlin.time.Instant

data class Question(
    val id: Long,
    val title: String,
    val category: Category,
    val sourceUrl: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isSolved: Boolean,
    val isLiked: Boolean,
) {
    fun isTitleMatched(query: SearchQuery): Boolean = title.contains(query.value, ignoreCase = true)
}
