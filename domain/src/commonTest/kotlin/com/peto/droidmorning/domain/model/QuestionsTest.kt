package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.assertAll
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Instant

class QuestionsTest {
    private lateinit var emptyQuestions: Questions
    private lateinit var questionsWithThreeItems: Questions
    private lateinit var mixedCategoryQuestions: Questions
    private lateinit var mixedSolvedQuestions: Questions
    private lateinit var mixedFavoriteQuestions: Questions

    @BeforeTest
    fun setup() {
        emptyQuestions = Questions(emptyList())

        questionsWithThreeItems =
            Questions(
                listOf(
                    createQuestion(id = 1),
                    createQuestion(id = 2),
                    createQuestion(id = 3),
                ),
            )

        mixedCategoryQuestions =
            Questions(
                listOf(
                    createQuestion(id = 1, category = Category.Kotlin),
                    createQuestion(id = 2, category = Category.Android),
                    createQuestion(id = 3, category = Category.Kotlin),
                ),
            )

        mixedSolvedQuestions =
            Questions(
                listOf(
                    createQuestion(id = 1, isSolved = true),
                    createQuestion(id = 2, isSolved = false),
                    createQuestion(id = 3, isSolved = true),
                ),
            )

        mixedFavoriteQuestions =
            Questions(
                listOf(
                    createQuestion(id = 1, isFavorite = true),
                    createQuestion(id = 2, isFavorite = false),
                    createQuestion(id = 3, isFavorite = true),
                ),
            )
    }

    private fun createQuestion(
        id: Long = 1L,
        title: String = "Test Question",
        category: Category = Category.Kotlin,
        isSolved: Boolean = false,
        isFavorite: Boolean = false,
    ) = Question(
        id = id,
        title = title,
        category = category,
        sourceUrl = "https://example.com",
        createdAt = Instant.fromEpochMilliseconds(0),
        updatedAt = Instant.fromEpochMilliseconds(0),
        isSolved = isSolved,
        isFavorite = isFavorite,
    )

    @Test
    fun `빈 Questions의 크기는 0이다`() {
        // Given

        // When
        val result = emptyQuestions.size

        // Then
        assertEquals(0, result)
    }

    @Test
    fun `3개의 질문을 가진 Questions의 크기는 3이다`() {
        // Given

        // When
        val result = questionsWithThreeItems.size

        // Then
        assertEquals(3, result)
    }

    @Test
    fun `빈 Questions는 비어있다고 판단한다`() {
        // Given

        // When
        val result = emptyQuestions.isEmpty

        // Then
        assertTrue(result)
    }

    @Test
    fun `항목이 있는 Questions는 비어있지 않다고 판단한다`() {
        // Given

        // When
        val result = questionsWithThreeItems.isEmpty

        // Then
        assertFalse(result)
    }

    @Test
    fun `검색어 필터를 적용하면 매칭되는 질문만 반환한다`() {
        // Given
        val questions =
            Questions(
                listOf(
                    createQuestion(id = 1, title = "What is Kotlin?"),
                    createQuestion(id = 2, title = "What is Android?"),
                    createQuestion(id = 3, title = "Kotlin Coroutines"),
                ),
            )
        val filter = Filter(searchQuery = SearchQuery("Kotlin"))

        // When
        val result = questions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().any { it.id == 1L }) },
            { assertTrue(result.toList().any { it.id == 3L }) },
        )
    }

    @Test
    fun `카테고리 필터를 적용하면 해당 카테고리의 질문만 반환한다`() {
        // Given
        val filter = Filter(categories = Categories(setOf(Category.Kotlin)))

        // When
        val result = mixedCategoryQuestions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().all { it.category == Category.Kotlin }) },
        )
    }

    @Test
    fun `풀이 완료 필터를 적용하면 풀이 완료된 질문만 반환한다`() {
        // Given
        val filter = Filter(showSolvedOnly = true)

        // When
        val result = mixedSolvedQuestions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().all { it.isSolved }) },
        )
    }

    @Test
    fun `즐겨찾기 필터를 적용하면 즐겨찾기된 질문만 반환한다`() {
        // Given
        val filter = Filter(showFavoritesOnly = true)

        // When
        val result = mixedFavoriteQuestions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().all { it.isFavorite }) },
        )
    }

    @Test
    fun `여러 필터를 동시에 적용하면 모든 조건을 만족하는 질문만 반환한다`() {
        // Given
        val questions =
            Questions(
                listOf(
                    createQuestion(id = 1, title = "Kotlin Basics", category = Category.Kotlin, isSolved = true),
                    createQuestion(id = 2, title = "Android Basics", category = Category.Android, isSolved = true),
                    createQuestion(id = 3, title = "Kotlin Advanced", category = Category.Kotlin, isSolved = false),
                    createQuestion(id = 4, title = "Kotlin Expert", category = Category.Kotlin, isSolved = true),
                ),
            )
        val filter =
            Filter(
                searchQuery = SearchQuery("Kotlin"),
                categories = Categories(setOf(Category.Kotlin)),
                showSolvedOnly = true,
            )

        // When
        val result = questions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().all { it.title.contains("Kotlin") }) },
            { assertTrue(result.toList().all { it.category == Category.Kotlin }) },
            { assertTrue(result.toList().all { it.isSolved }) },
        )
    }

    @Test
    fun `빈 필터를 적용하면 모든 질문을 반환한다`() {
        // Given
        val filter = Filter()

        // When
        val result = questionsWithThreeItems.applyFilters(filter)

        // Then
        assertEquals(3, result.size)
    }

    @Test
    fun `빈 검색어로 필터링하면 모든 질문을 반환한다`() {
        // Given
        val questions =
            Questions(
                listOf(
                    createQuestion(id = 1, title = "Kotlin"),
                    createQuestion(id = 2, title = "Android"),
                ),
            )
        val filter = Filter(searchQuery = SearchQuery(""))

        // When
        val result = questions.applyFilters(filter)

        // Then
        assertEquals(2, result.size)
    }

    @Test
    fun `여러 카테고리로 필터링하면 해당 카테고리들의 질문을 반환한다`() {
        // Given
        val questions =
            Questions(
                listOf(
                    createQuestion(id = 1, category = Category.Kotlin),
                    createQuestion(id = 2, category = Category.Android),
                    createQuestion(id = 3, category = Category.Compose),
                    createQuestion(id = 4, category = Category.Coroutine),
                ),
            )
        val filter =
            Filter(
                categories = Categories(setOf(Category.Kotlin, Category.Android)),
            )

        // When
        val result = questions.applyFilters(filter)

        // Then
        assertAll(
            { assertEquals(2, result.size) },
            { assertTrue(result.toList().any { it.category == Category.Kotlin }) },
            { assertTrue(result.toList().any { it.category == Category.Android }) },
        )
    }

    @Test
    fun `Questions를 리스트로 변환하면 올바른 리스트를 반환한다`() {
        // Given
        val questionList =
            listOf(
                createQuestion(id = 1),
                createQuestion(id = 2),
            )
        val questions = Questions(questionList)

        // When
        val result = questions.toList()

        // Then
        assertEquals(questionList, result)
    }
}
