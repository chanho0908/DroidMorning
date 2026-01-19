package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamDetailResponse(
    @SerialName("exam_item_id")
    val examItemId: Long,
    @SerialName("exam_id")
    val examId: Long,
    @SerialName("question_id")
    val questionId: Long,
    @SerialName("user_answer")
    val userAnswer: String,
    @SerialName("question_title")
    val questionTitle: String,
    @SerialName("question_category")
    val questionCategory: String,
    @SerialName("question_source_url")
    val questionSourceUrl: String,
)

fun ExamDetailResponse.toDomain(): ExamDetail =
    ExamDetail(
        examItemId = examItemId,
        examId = examId,
        questionId = questionId,
        userAnswer = userAnswer,
        questionTitle = questionTitle,
        questionCategory = Category.from(questionCategory),
        questionSourceUrl = questionSourceUrl,
    )
