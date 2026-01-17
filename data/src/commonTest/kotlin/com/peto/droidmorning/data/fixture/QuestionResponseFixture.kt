package com.peto.droidmorning.data.fixture

import com.peto.droidmorning.data.model.response.QuestionResponse
import com.peto.droidmorning.domain.model.Category
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
}
