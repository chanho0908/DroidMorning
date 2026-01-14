package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.domain.model.Category
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

    override suspend fun fetchQuestionsByCategory(category: Category): Result<Questions> =
        runCatching {
            val result =
                remoteQuestionDataSource
                    .fetchQuestionsByCategory(category.name)
                    .map { response -> response.toDomain() }
            Questions(result)
        }

    override suspend fun searchQuestions(query: String): Result<Questions> =
        runCatching {
            val result =
                remoteQuestionDataSource
                    .searchQuestions(query)
                    .map { response -> response.toDomain() }
            Questions(result)
        }
}
