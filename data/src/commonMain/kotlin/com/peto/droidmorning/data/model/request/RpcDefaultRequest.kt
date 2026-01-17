package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RpcDefaultRequest(
    @SerialName("p_user_id")
    val userId: String,
    @SerialName("p_question_id")
    val questionId: Long,
)
