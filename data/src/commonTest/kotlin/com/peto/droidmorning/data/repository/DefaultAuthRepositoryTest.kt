package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.fake.FakeLocalAuthDataSource
import com.peto.droidmorning.data.fake.FakeRemoteAuthDataSource
import com.peto.droidmorning.domain.repository.auth.AuthType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
            fakeLocalDataSource.saveTokens("accessToken", "refreshToken")

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
}
