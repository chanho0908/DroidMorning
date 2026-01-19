package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamHistory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ExamHistoryResponse(
    val id: Long,
    @SerialName("total_questions")
    val totalQuestions: Int,
    val categories: List<String>,
    @SerialName("created_at")
    val createdAt: Instant,
)

fun ExamHistoryResponse.toDomain(): ExamHistory =
    ExamHistory(
        id = id,
        exampleCount = totalQuestions,
        categories =
            categories.mapNotNull { categoryString ->
                runCatching { Category.from(categoryString) }.getOrNull()
            },
        createdAt = createdAt,
    )
