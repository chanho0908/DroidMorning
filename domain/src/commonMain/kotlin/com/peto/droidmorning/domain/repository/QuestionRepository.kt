package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Questions

interface QuestionRepository {
    suspend fun fetchQuestions(): Result<Questions>

    suspend fun fetchQuestionsByCategory(category: Category): Result<Questions>

    suspend fun searchQuestions(query: String): Result<Questions>
}
