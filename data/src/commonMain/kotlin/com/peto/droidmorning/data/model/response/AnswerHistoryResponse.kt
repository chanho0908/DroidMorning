package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.Answer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class AnswerHistoryResponse(
    val id: Long,
    @SerialName("user_id")
    val userId: String,
    @SerialName("question_id")
    val questionId: Long,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
) {
    fun toDomain(): Answer.History =
        Answer.History(
            id = id,
            userId = userId,
            questionId = questionId,
            content = content,
            createdAt = Instant.parse(createdAt),
        )
}
