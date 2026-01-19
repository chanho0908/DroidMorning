package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.question.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class QuestionResponse(
    val id: Long,
    val title: String,
    val category: String,
    @SerialName("source_url")
    val sourceUrl: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant,
    @SerialName("is_favorited")
    val isLiked: Boolean,
    @SerialName("is_solved")
    val isSolved: Boolean,
) {
    fun toDomain(): Question =
        Question(
            id = id,
            title = title,
            category = Category.from(category),
            sourceUrl = sourceUrl,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isSolved = isSolved,
            isLiked = isLiked,
        )
}
