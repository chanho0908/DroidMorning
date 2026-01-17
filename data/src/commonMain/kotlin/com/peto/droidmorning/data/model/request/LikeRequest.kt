package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeRequest(
    @SerialName("user_id")
    val userId: String,
    @SerialName("question_id")
    val questionId: Long,
)
