package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamQuestion
import com.peto.droidmorning.domain.model.question.Questions

interface QuestionRepository {
    suspend fun fetchQuestions(): Result<Questions>

    suspend fun fetchExamQuestions(
        questionCount: Int,
        categories: List<Category>,
    ): Result<List<ExamQuestion>>

    suspend fun toggleQuestionLike(
        questionId: Long,
        isCurrentlyLiked: Boolean,
    ): Result<Boolean>

    suspend fun fetchAllCategoryCount(): Result<Map<Category, Long>>
}
