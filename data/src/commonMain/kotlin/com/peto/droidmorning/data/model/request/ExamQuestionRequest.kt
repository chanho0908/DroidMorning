package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamQuestionRequest(
    @SerialName("selected_categories")
    val categories: List<String>,
    @SerialName("total_count")
    val count: Int,
)
