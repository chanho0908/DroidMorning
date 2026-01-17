package com.peto.droidmorning.domain.model

import kotlin.time.Instant

sealed class Answer {
    abstract val questionId: Long
    abstract val content: String
    abstract val createdAt: Instant
    abstract val updatedAt: Instant

    data class Current(
        val userId: String,
        override val questionId: Long,
        override val content: String,
        override val updatedAt: Instant,
    ) : Answer() {
        override val createdAt: Instant
            get() = updatedAt
    }

    data class History(
        val id: Long,
        val userId: String,
        override val questionId: Long,
        override val content: String,
        override val createdAt: Instant,
    ) : Answer() {
        override val updatedAt: Instant
            get() = createdAt
    }
}
