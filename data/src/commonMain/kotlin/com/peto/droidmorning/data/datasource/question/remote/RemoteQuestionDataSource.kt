package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.response.QuestionResponse

interface RemoteQuestionDataSource {
    suspend fun fetchQuestions(): List<QuestionResponse>

    suspend fun addLike(questionId: Long)

    suspend fun removeLike(questionId: Long)
}
