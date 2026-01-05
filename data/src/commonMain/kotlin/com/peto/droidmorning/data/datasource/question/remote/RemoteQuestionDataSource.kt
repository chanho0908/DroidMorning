package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.QuestionResponse

interface RemoteQuestionDataSource {
    suspend fun fetchQuestions(): List<QuestionResponse>

    suspend fun fetchQuestionsByCategory(category: String): List<QuestionResponse>

    suspend fun searchQuestions(query: String): List<QuestionResponse>
}
