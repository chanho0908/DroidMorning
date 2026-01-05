package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.fake.FakeRemoteQuestionDataSource
import com.peto.droidmorning.data.fixture.QuestionResponseFixture
import com.peto.droidmorning.domain.model.Category
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultQuestionRepositoryTest {
    @Test
    fun `fetchQuestions 성공 시 Question 리스트를 반환한다`() =
        runTest {
            // given
            val responses = QuestionResponseFixture.questionResponseList()
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.fetchQuestions()

            // then
            assertTrue(result.isSuccess)
            val questions = result.getOrThrow()
            assertEquals(3, questions.size)
            assertEquals("질문 1", questions.first().title)
        }

    @Test
    fun `fetchQuestionsByCategory Kotlin은 Kotlin 카테고리만 반환한다`() =
        runTest {
            // given
            val responses =
                listOf(
                    QuestionResponseFixture.questionResponse(
                        id = 1L,
                        title = "match-${Category.Kotlin.name}",
                        category = Category.Kotlin,
                    ),
                    QuestionResponseFixture.questionResponse(
                        id = 2L,
                        title = "other-${Category.Android.name}",
                        category = Category.Android,
                    ),
                )
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.fetchQuestionsByCategory(Category.Kotlin)

            // then
            val questions = result.getOrThrow()
            assertEquals(1, questions.size)
            assertEquals(Category.Kotlin, questions.first().category)
        }

    @Test
    fun `fetchQuestionsByCategory Coroutine은 Coroutine 카테고리만 반환한다`() =
        runTest {
            // given
            val responses =
                listOf(
                    QuestionResponseFixture.questionResponse(
                        id = 1L,
                        title = "match-${Category.Coroutine.name}",
                        category = Category.Coroutine,
                    ),
                    QuestionResponseFixture.questionResponse(
                        id = 2L,
                        title = "other-${Category.Compose.name}",
                        category = Category.Compose,
                    ),
                )
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.fetchQuestionsByCategory(Category.Coroutine)

            // then
            val questions = result.getOrThrow()
            assertEquals(1, questions.size)
            assertEquals(Category.Coroutine, questions.first().category)
        }

    @Test
    fun `fetchQuestionsByCategory Android는 Android 카테고리만 반환한다`() =
        runTest {
            // given
            val responses =
                listOf(
                    QuestionResponseFixture.questionResponse(
                        id = 1L,
                        title = "match-${Category.Android.name}",
                        category = Category.Android,
                    ),
                    QuestionResponseFixture.questionResponse(
                        id = 2L,
                        title = "other-${Category.Kotlin.name}",
                        category = Category.Kotlin,
                    ),
                )
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.fetchQuestionsByCategory(Category.Android)

            // then
            val questions = result.getOrThrow()
            assertEquals(1, questions.size)
            assertEquals(Category.Android, questions.first().category)
        }

    @Test
    fun `fetchQuestionsByCategory Compose는 Compose 카테고리만 반환한다`() =
        runTest {
            // given
            val responses =
                listOf(
                    QuestionResponseFixture.questionResponse(
                        id = 1L,
                        title = "match-${Category.Compose.name}",
                        category = Category.Compose,
                    ),
                    QuestionResponseFixture.questionResponse(
                        id = 2L,
                        title = "other-${Category.Coroutine.name}",
                        category = Category.Coroutine,
                    ),
                )
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.fetchQuestionsByCategory(Category.Compose)

            // then
            val questions = result.getOrThrow()
            assertEquals(1, questions.size)
            assertEquals(Category.Compose, questions.first().category)
        }

    @Test
    fun `searchQuestions는 query가 포함된 질문만 반환한다`() =
        runTest {
            // given
            val responses =
                listOf(
                    QuestionResponseFixture.questionResponse(title = "Kotlin Coroutines"),
                    QuestionResponseFixture.questionResponse(title = "Swift Concurrency"),
                )
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)

            // when
            val result = repository.searchQuestions("Kotlin")

            // then
            val questions = result.getOrThrow()
            assertEquals(1, questions.size)
            assertEquals("Kotlin Coroutines", questions.first().title)
        }
}
