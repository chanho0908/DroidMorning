package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.domain.model.Questions
import com.peto.droidmorning.domain.repository.QuestionRepository

class DefaultQuestionRepository(
    private val remoteQuestionDataSource: RemoteQuestionDataSource,
) : QuestionRepository {
    override suspend fun fetchQuestions(): Result<Questions> =
        runCatching {
            val result =
                remoteQuestionDataSource
                    .fetchQuestions()
                    .map { it.toDomain() }
            Questions(result)
        }

    override suspend fun toggleQuestionLike(
        questionId: Long,
        isCurrentlyLiked: Boolean,
    ): Result<Boolean> =
        runCatching {
            if (isCurrentlyLiked) {
                remoteQuestionDataSource.removeLike(questionId)
            } else {
                remoteQuestionDataSource.addLike(questionId)
            }
            true
        }
}
