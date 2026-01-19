package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.response.CategoryCountResponse
import com.peto.droidmorning.data.model.response.ExamQuestionResponse
import com.peto.droidmorning.data.model.response.QuestionResponse

interface RemoteQuestionDataSource {
    suspend fun fetchExamQuestions(): List<QuestionResponse>

    suspend fun fetchExamQuestions(
        category: List<String>,
        count: Int,
    ): List<ExamQuestionResponse>

    suspend fun addLike(questionId: Long)

    suspend fun removeLike(questionId: Long)

    suspend fun fetchCategoryCount(): List<CategoryCountResponse>
}
