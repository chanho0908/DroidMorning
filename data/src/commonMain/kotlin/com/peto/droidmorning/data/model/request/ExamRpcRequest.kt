package com.peto.droidmorning.data.model.request

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.Exams
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamRpcRequest(
    val uid: String,
    @SerialName("total_questions")
    val questionsCount: Int,
    val categories: List<Category>,
    val items: List<ExamItemRpcRequest>,
)

fun Exams.toRequest(
    uid: String,
    categories: List<Category>,
): ExamRpcRequest =
    ExamRpcRequest(
        uid = uid,
        questionsCount = values.size,
        categories = categories,
        items = values.map { it.toRequest() },
    )
