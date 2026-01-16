package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.Answer

interface AnswerRepository {
    suspend fun fetchCurrentAnswer(questionId: Long): Result<Answer.Current?>

    suspend fun fetchAnswerHistory(questionId: Long): Result<List<Answer.History>>

    suspend fun saveAnswer(
        questionId: Long,
        content: String,
    ): Result<Unit>

    suspend fun updateAnswer(
        questionId: Long,
        content: String,
    ): Result<Unit>

    suspend fun deleteCurrentAnswer(questionId: Long): Result<Unit>

    suspend fun deleteAnswerHistory(historyId: Long): Result<Unit>
}
