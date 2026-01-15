package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.fake.FakeRemoteQuestionDataSource
import com.peto.droidmorning.data.fixture.QuestionResponseFixture
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
            assertEquals("질문 1", questions.toList().first().title)
        }

    @Test
    fun `toggleQuestionLike는 좋아요가 추가되면 true를 반환한다`() =
        runTest {
            // given
            val responses = QuestionResponseFixture.questionResponseList()
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)
            val questionId = 1L

            // when
            val result = repository.toggleQuestionLike(questionId, isCurrentlyLiked = false)

            // then
            assertTrue(result.isSuccess)
            assertTrue(result.getOrThrow())
            assertTrue(fakeDataSource.isLiked(questionId))
        }

    @Test
    fun `toggleQuestionLike는 좋아요가 제거되면 true를 반환한다`() =
        runTest {
            // given
            val responses = QuestionResponseFixture.questionResponseList()
            val fakeDataSource = FakeRemoteQuestionDataSource(questions = responses)
            val repository = DefaultQuestionRepository(fakeDataSource)
            val questionId = 1L

            // 먼저 좋아요 추가
            fakeDataSource.addLike(questionId)

            // when
            val result = repository.toggleQuestionLike(questionId, isCurrentlyLiked = true)

            // then
            assertTrue(result.isSuccess)
            assertTrue(result.getOrThrow())
            assertTrue(!fakeDataSource.isLiked(questionId))
        }
}
