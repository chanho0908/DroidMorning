package com.peto.droidmorning.data.model.response

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamQuestion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamQuestionResponse(
    @SerialName("question_id")
    val questionId: Long,
    @SerialName("question_content")
    val content: String,
    val category: String,
) {
    fun toDomain(): ExamQuestion =
        ExamQuestion(
            questionId = questionId,
            content = content,
            category = Category.from(category),
        )
}
