package com.peto.droidmorning.domain.model

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SearchQueryTest {
    private lateinit var emptySearchQuery: SearchQuery
    private lateinit var blankSearchQuery: SearchQuery
    private lateinit var validSearchQuery: SearchQuery

    @BeforeTest
    fun setup() {
        emptySearchQuery = SearchQuery("")
        blankSearchQuery = SearchQuery("   ")
        validSearchQuery = SearchQuery("kotlin")
    }

    @Test
    fun `빈 문자열로 생성된 SearchQuery는 비어있다고 판단한다`() {
        // Given

        // When
        val result = emptySearchQuery.isEmpty()

        // Then
        assertTrue(result)
    }

    @Test
    fun `공백 문자열로 생성된 SearchQuery는 비어있다고 판단한다`() {
        // Given

        // When
        val result = blankSearchQuery.isEmpty()

        // Then
        assertTrue(result)
    }

    @Test
    fun `유효한 문자열로 생성된 SearchQuery는 비어있지 않다고 판단한다`() {
        // Given

        // When
        val result = validSearchQuery.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `탭과 줄바꿈만 포함된 문자열은 비어있다고 판단한다`() {
        // Given
        val whitespaceQuery = SearchQuery("\t\n  ")

        // When
        val result = whitespaceQuery.isEmpty()

        // Then
        assertTrue(result)
    }
}
