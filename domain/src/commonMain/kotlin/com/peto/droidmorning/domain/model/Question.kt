package com.peto.droidmorning.domain.model

import kotlin.time.Instant

data class Question(
    val id: Long,
    val title: String,
    val category: String,
    val sourceUrl: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
