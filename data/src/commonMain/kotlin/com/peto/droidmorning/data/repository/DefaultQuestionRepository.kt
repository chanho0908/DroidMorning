package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamQuestion
import com.peto.droidmorning.domain.model.question.Questions
import com.peto.droidmorning.domain.repository.QuestionRepository

class DefaultQuestionRepository(
    private val remoteQuestionDataSource: RemoteQuestionDataSource,
) : QuestionRepository {
    override suspend fun fetchQuestions(): Result<Questions> =
        runCatching {
            val result =
                remoteQuestionDataSource
                    .fetchExamQuestions()
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

    override suspend fun fetchAllCategoryCount(): Result<Map<Category, Long>> =
        runCatching {
            remoteQuestionDataSource
                .fetchCategoryCount()
                .associate { response ->
                    val category = Category.from(response.category)
                    category to response.count
                }
        }

    override suspend fun fetchExamQuestions(
        questionCount: Int,
        categories: List<Category>,
    ): Result<List<ExamQuestion>> =
        runCatching {
            val categoryNames = categories.map { it.name }

            remoteQuestionDataSource
                .fetchExamQuestions(
                    category = categoryNames,
                    count = questionCount,
                ).map { it.toDomain() }
        }
}
