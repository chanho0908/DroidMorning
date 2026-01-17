package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.Answer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class CurrentAnswerResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("question_id")
    val questionId: Long,
    val content: String,
    @SerialName("updated_at")
    val updatedAt: String,
) {
    fun toDomain(): Answer.Current =
        Answer.Current(
            userId = userId,
            questionId = questionId,
            content = content,
            updatedAt = Instant.parse(updatedAt),
        )
}
