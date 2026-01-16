package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAnswerRequest(
    @SerialName("content")
    val content: String,
)
