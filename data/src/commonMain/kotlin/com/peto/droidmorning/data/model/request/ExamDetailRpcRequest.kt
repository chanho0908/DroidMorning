package com.peto.droidmorning.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamDetailRpcRequest(
    @SerialName("p_exam_id")
    val examId: Long,
)
