package com.peto.droidmorning.data.model.request

import com.peto.droidmorning.domain.model.exam.Exam
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamItemRpcRequest(
    @SerialName("question_id")
    val questionId: Long,
    @SerialName("user_answer")
    val answer: String,
)

fun Exam.toRequest(): ExamItemRpcRequest =
    ExamItemRpcRequest(
        questionId = questionId,
        answer = answer,
    )
