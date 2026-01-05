package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Question
import com.peto.droidmorning.domain.repository.QuestionRepository

class DefaultQuestionRepository(
    private val remoteQuestionDataSource: RemoteQuestionDataSource,
) : QuestionRepository {
    override suspend fun fetchQuestions(): Result<List<Question>> =
        runCatching {
            remoteQuestionDataSource
                .fetchQuestions()
                .map { it.toDomain() }
        }

    override suspend fun fetchQuestionsByCategory(category: Category): Result<List<Question>> =
        runCatching {
            remoteQuestionDataSource
                .fetchQuestionsByCategory(category.name)
                .map { response -> response.toDomain() }
        }

    override suspend fun searchQuestions(query: String): Result<List<Question>> =
        runCatching {
            remoteQuestionDataSource
                .searchQuestions(query)
                .map { response -> response.toDomain() }
        }
}
