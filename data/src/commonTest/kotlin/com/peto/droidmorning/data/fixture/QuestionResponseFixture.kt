package com.peto.droidmorning.data.fixture

import com.peto.droidmorning.data.model.response.CategoryCountResponse
import com.peto.droidmorning.data.model.response.ExamQuestionResponse
import com.peto.droidmorning.data.model.response.QuestionResponse
import com.peto.droidmorning.domain.model.category.Category
import kotlin.time.Instant

object QuestionResponseFixture {
    fun questionResponse(
        id: Long = 1L,
        title: String = "테스트 질문",
        category: Category = Category.Android,
        sourceUrl: String = "https://example.com",
        createdAt: Instant = Instant.parse("2024-01-01T00:00:00Z"),
        updatedAt: Instant = Instant.parse("2024-01-01T00:00:00Z"),
    ): QuestionResponse =
        QuestionResponse(
            id = id,
            title = title,
            category = category.name,
            sourceUrl = sourceUrl,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isLiked = true,
            isSolved = true,
        )

    fun questionResponseList(size: Int = 3): List<QuestionResponse> =
        (1..size).map {
            questionResponse(
                id = it.toLong(),
                title = "질문 $it",
            )
        }

    fun examQuestionResponse(
        questionId: Long = 1L,
        content: String = "시험 질문 내용",
        category: Category = Category.Kotlin,
    ): ExamQuestionResponse =
        ExamQuestionResponse(
            questionId = questionId,
            content = content,
            category = category.name,
        )

    fun examQuestionResponseList(size: Int = 5): List<ExamQuestionResponse> =
        (1..size).map {
            examQuestionResponse(
                questionId = it.toLong(),
                content = "시험 질문 $it",
                category = Category.entries[it % Category.entries.size],
            )
        }

    fun categoryCountResponse(
        category: Category = Category.Kotlin,
        count: Long = 10L,
    ): CategoryCountResponse =
        CategoryCountResponse(
            category = category.name,
            count = count,
        )

    fun categoryCountResponseList(): List<CategoryCountResponse> =
        Category.entries.mapIndexed { index, category ->
            categoryCountResponse(
                category = category,
                count = (index + 1) * 10L,
            )
        }
}
