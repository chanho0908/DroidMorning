package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.fixture.QuestionResponseFixture
import com.peto.droidmorning.domain.model.category.Category
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FakeRemoteQuestionDataSourceTest {
    private lateinit var dataSource: FakeRemoteQuestionDataSource

    @BeforeTest
    fun setup() {
        dataSource =
            FakeRemoteQuestionDataSource(
                questions = QuestionResponseFixture.questionResponseList(3),
                examQuestions = QuestionResponseFixture.examQuestionResponseList(10),
                categoryCount = QuestionResponseFixture.categoryCountResponseList(),
            )
    }

    @Test
    fun `fetchExamQuestions는 모든 질문을 반환한다`() =
        runTest {
            // When
            val result = dataSource.fetchExamQuestions()

            // Then
            assertEquals(3, result.size)
            assertEquals("질문 1", result[0].title)
        }

    @Test
    fun `fetchExamQuestions with categories는 지정된 카테고리의 질문만 반환한다`() =
        runTest {
            // Given
            val categories = listOf(Category.Kotlin.name, Category.Android.name)
            val count = 5

            // When
            val result = dataSource.fetchExamQuestions(category = categories, count = count)

            // Then
            assertTrue(result.size <= count)
            assertTrue(result.all { it.category in categories })
        }

    @Test
    fun `fetchExamQuestions with empty categories는 모든 카테고리에서 count만큼 반환한다`() =
        runTest {
            // Given
            val count = 3

            // When
            val result = dataSource.fetchExamQuestions(category = emptyList(), count = count)

            // Then
            assertEquals(count, result.size)
        }

    @Test
    fun `fetchExamQuestions는 count보다 적은 질문이 있으면 가능한 만큼만 반환한다`() =
        runTest {
            // Given
            val smallDataSource =
                FakeRemoteQuestionDataSource(
                    examQuestions = QuestionResponseFixture.examQuestionResponseList(3),
                )
            val count = 10

            // When
            val result = smallDataSource.fetchExamQuestions(category = emptyList(), count = count)

            // Then
            assertEquals(3, result.size)
        }

    @Test
    fun `addLike는 질문 ID를 좋아요 목록에 추가한다`() =
        runTest {
            // Given
            val questionId = 1L

            // When
            dataSource.addLike(questionId)

            // Then
            assertTrue(dataSource.isLiked(questionId))
        }

    @Test
    fun `removeLike는 질문 ID를 좋아요 목록에서 제거한다`() =
        runTest {
            // Given
            val questionId = 1L
            dataSource.addLike(questionId)

            // When
            dataSource.removeLike(questionId)

            // Then
            assertFalse(dataSource.isLiked(questionId))
        }

    @Test
    fun `clearLikes는 모든 좋아요를 제거한다`() =
        runTest {
            // Given
            dataSource.addLike(1L)
            dataSource.addLike(2L)
            dataSource.addLike(3L)

            // When
            dataSource.clearLikes()

            // Then
            assertFalse(dataSource.isLiked(1L))
            assertFalse(dataSource.isLiked(2L))
            assertFalse(dataSource.isLiked(3L))
        }

    @Test
    fun `fetchCategoryCount는 모든 카테고리의 개수를 반환한다`() =
        runTest {
            // When
            val result = dataSource.fetchCategoryCount()

            // Then
            assertEquals(Category.entries.size, result.size)
            assertEquals(Category.Kotlin.name, result[0].category)
            assertEquals(10L, result[0].count)
        }

    @Test
    fun `isLiked는 좋아요하지 않은 질문에 대해 false를 반환한다`() {
        // Given
        val questionId = 999L

        // When
        val result = dataSource.isLiked(questionId)

        // Then
        assertFalse(result)
    }

    @Test
    fun `동일한 질문에 여러 번 addLike를 호출해도 중복되지 않는다`() =
        runTest {
            // Given
            val questionId = 1L

            // When
            dataSource.addLike(questionId)
            dataSource.addLike(questionId)
            dataSource.addLike(questionId)

            // Then
            assertTrue(dataSource.isLiked(questionId))
        }
}
