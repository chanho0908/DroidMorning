package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.fake.FakeLocalAuthDataSource
import com.peto.droidmorning.data.fake.FakeRemoteAuthDataSource
import com.peto.droidmorning.domain.repository.auth.AuthType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultAuthRepositoryTest {
    private lateinit var repository: DefaultAuthRepository
    private lateinit var fakeRemoteDataSource: FakeRemoteAuthDataSource
    private lateinit var fakeLocalDataSource: FakeLocalAuthDataSource

    @BeforeTest
    fun setup() {
        fakeRemoteDataSource = FakeRemoteAuthDataSource()
        fakeLocalDataSource = FakeLocalAuthDataSource()
        repository =
            DefaultAuthRepository(
                remoteDataSource = fakeRemoteDataSource,
                localDataSource = fakeLocalDataSource,
            )
    }

    @Test
    fun `토큰이 존재하면 Authenticated를 반환 한다`() =
        runTest {
            // given
            fakeLocalDataSource.saveTokens(
                com.peto.droidmorning.domain.model.AuthToken(
                    accessToken = "accessToken",
                    refreshToken = "refreshToken",
                ),
            )

            // when
            val actual = repository.authType()

            // then
            assertEquals(AuthType.Authenticated, actual)
        }

    @Test
    fun `토큰이 존재하지 않으면 UnAuthenticated를 반환 한다`() =
        runTest {
            // when
            val actual = repository.authType()

            // then
            assertEquals(AuthType.Unauthenticated, actual)
        }

    @Test
    fun `로그인에 성공하면 액세스 토큰과 리프레시 토큰을 저장한다`() =
        runTest {
            // given
            val oauthIdToken = "oauthIdToken"
            assertFalse(fakeLocalDataSource.hasToken())

            // when
            repository.signIn(oauthIdToken)

            // then
            fakeLocalDataSource.hasToken()
        }

    @Test
    fun `로그아웃에 성공하면 Result Success를 반환한다`() =
        runTest {
            // when
            val result = repository.signOut()

            // then
            assertTrue(result.isSuccess)
        }
}
