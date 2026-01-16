package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.answer.remote.RemoteAnswerDataSource
import com.peto.droidmorning.domain.model.Answer
import com.peto.droidmorning.domain.repository.AnswerRepository

class DefaultAnswerRepository(
    private val remoteDataSource: RemoteAnswerDataSource,
) : AnswerRepository {
    override suspend fun fetchCurrentAnswer(questionId: Long): Result<Answer.Current?> =
        runCatching {
            remoteDataSource.fetchCurrentAnswer(questionId)?.toDomain()
        }

    override suspend fun fetchAnswerHistory(questionId: Long): Result<List<Answer.History>> =
        runCatching {
            remoteDataSource
                .fetchAnswerHistory(questionId)
                .map { it.toDomain() }
        }

    override suspend fun saveAnswer(
        questionId: Long,
        content: String,
    ): Result<Unit> = runCatching { remoteDataSource.createAnswer(questionId, content) }

    override suspend fun updateAnswer(
        questionId: Long,
        content: String,
    ): Result<Unit> = runCatching { remoteDataSource.modifyAnswer(questionId, content) }

    override suspend fun deleteCurrentAnswer(questionId: Long): Result<Unit> =
        runCatching {
            remoteDataSource.deleteCurrentAnswer(questionId)
        }

    override suspend fun deleteAnswerHistory(historyId: Long): Result<Unit> =
        runCatching {
            remoteDataSource.deleteAnswerHistory(historyId)
        }
}
