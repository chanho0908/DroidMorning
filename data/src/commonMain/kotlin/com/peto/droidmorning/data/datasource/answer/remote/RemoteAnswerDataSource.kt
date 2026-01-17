package com.peto.droidmorning.data.datasource.answer.remote

import com.peto.droidmorning.data.model.response.AnswerHistoryResponse
import com.peto.droidmorning.data.model.response.CurrentAnswerResponse

interface RemoteAnswerDataSource {
    suspend fun fetchCurrentAnswer(questionId: Long): CurrentAnswerResponse?

    suspend fun fetchAnswerHistory(questionId: Long): List<AnswerHistoryResponse>

    suspend fun createAnswer(
        questionId: Long,
        content: String,
    )

    suspend fun modifyAnswer(
        questionId: Long,
        content: String,
    )

    suspend fun deleteCurrentAnswer(questionId: Long)

    suspend fun deleteAnswerHistory(historyId: Long)
}
