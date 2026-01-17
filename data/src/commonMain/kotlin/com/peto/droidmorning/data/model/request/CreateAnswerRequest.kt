package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAnswerRequest(
    @SerialName("p_user_id")
    val userId: String,
    @SerialName("p_question_id")
    val questionId: Long,
    @SerialName("p_content")
    val content: String,
)
