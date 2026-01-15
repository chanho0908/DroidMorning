package com.peto.droidmorning.domain.model

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Instant

class QuestionTest {
    private lateinit var testQuestion: Question

    @BeforeTest
    fun setup() {
        testQuestion = createQuestion(title = "What is Kotlin coroutine?")
    }

    private fun createQuestion(
        id: Long = 1L,
        title: String = "Test Question",
        category: Category = Category.Kotlin,
        sourceUrl: String = "https://example.com",
        isSolved: Boolean = false,
        isLiked: Boolean = false,
    ) = Question(
        id = id,
        title = title,
        category = category,
        sourceUrl = sourceUrl,
        createdAt = Instant.fromEpochMilliseconds(0),
        updatedAt = Instant.fromEpochMilliseconds(0),
        isSolved = isSolved,
        isLiked = isLiked,
    )

    @Test
    fun `질문 제목에 검색어가 포함되어 있으면 매칭된다고 판단한다`() {
        // Given
        val query = SearchQuery("kotlin")

        // When
        val result = testQuestion.isTitleMatched(query)

        // Then
        assertTrue(result)
    }

    @Test
    fun `질문 제목에 검색어가 포함되어 있지 않으면 매칭되지 않는다고 판단한다`() {
        // Given
        val question = createQuestion(title = "What is Android?")
        val query = SearchQuery("kotlin")

        // When
        val result = question.isTitleMatched(query)

        // Then
        assertFalse(result)
    }

    @Test
    fun `빈 검색어는 모든 질문과 매칭된다`() {
        // Given
        val query = SearchQuery("")

        // When
        val result = testQuestion.isTitleMatched(query)

        // Then
        assertTrue(result)
    }

    @Test
    fun `대소문자를 구분하지 않고 검색어 매칭을 수행한다`() {
        // Given
        val query = SearchQuery("KOTLIN")

        // When
        val result = testQuestion.isTitleMatched(query)

        // Then
        assertTrue(result)
    }

    @Test
    fun `부분 검색어로도 매칭이 가능하다`() {
        // Given
        val question = createQuestion(title = "Understanding Kotlin Coroutines")
        val query = SearchQuery("Coroutine")

        // When
        val result = question.isTitleMatched(query)

        // Then
        assertTrue(result)
    }

    @Test
    fun `공백이 포함된 검색어로도 매칭이 가능하다`() {
        // Given
        val question = createQuestion(title = "What is Kotlin Flow?")
        val query = SearchQuery("Kotlin Flow")

        // When
        val result = question.isTitleMatched(query)

        // Then
        assertTrue(result)
    }
}
