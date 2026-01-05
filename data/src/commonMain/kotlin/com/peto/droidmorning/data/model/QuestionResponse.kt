package com.peto.droidmorning.data.model

import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Question
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
) {
    fun toDomain(): Question =
        Question(
            id = id,
            title = title,
            category = Category.from(category),
            sourceUrl = sourceUrl,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
}
