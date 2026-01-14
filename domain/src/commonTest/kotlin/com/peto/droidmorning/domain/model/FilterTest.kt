package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.assertAll
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FilterTest {
    private lateinit var emptyFilter: Filter
    private lateinit var filterWithQuery: Filter
    private lateinit var filterWithCategory: Filter
    private lateinit var filterWithSolved: Filter
    private lateinit var filterWithFavorites: Filter

    @BeforeTest
    fun setup() {
        emptyFilter = Filter()
        filterWithQuery = Filter(searchQuery = SearchQuery("kotlin"))
        filterWithCategory = Filter(categories = Categories(setOf(Category.Kotlin)))
        filterWithSolved = Filter(showSolvedOnly = true)
        filterWithFavorites = Filter(showFavoritesOnly = true)
    }

    @Test
    fun `기본 Filter는 비어있다고 판단한다`() {
        // When
        val result = emptyFilter.isEmpty()

        // Then
        assertTrue(result)
    }

    @Test
    fun `검색어가 있는 Filter는 비어있지 않다고 판단한다`() {
        // When
        val result = filterWithQuery.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `카테고리가 있는 Filter는 비어있지 않다고 판단한다`() {
        // When
        val result = filterWithCategory.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `풀이 완료 필터가 활성화된 Filter는 비어있지 않다고 판단한다`() {
        // When
        val result = filterWithSolved.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `즐겨찾기 필터가 활성화된 Filter는 비어있지 않다고 판단한다`() {
        // When
        val result = filterWithFavorites.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `검색어를 업데이트하면 새로운 검색어를 가진 Filter를 반환한다`() {
        // Given
        val newQuery = "kotlin coroutine"

        // When
        val result = emptyFilter.updateSearchQuery(newQuery)

        // Then
        assertAll(
            { assertEquals(newQuery, result.searchQuery.value) },
            { assertEquals(emptyFilter.categories, result.categories) },
            { assertEquals(emptyFilter.showSolvedOnly, result.showSolvedOnly) },
            { assertEquals(emptyFilter.showFavoritesOnly, result.showFavoritesOnly) },
        )
    }

    @Test
    fun `카테고리가 없는 Filter에서 카테고리를 토글하면 카테고리가 추가된다`() {
        // Given

        // When
        val result = emptyFilter.toggleCategory(Category.Kotlin)

        // Then
        assertTrue(result.categories.contains(Category.Kotlin))
    }

    @Test
    fun `카테고리가 있는 Filter에서 같은 카테고리를 토글하면 제거된다`() {
        // Given

        // When
        val result = filterWithCategory.toggleCategory(Category.Kotlin)

        // Then
        assertAll(
            { assertFalse(result.categories.contains(Category.Kotlin)) },
            { assertTrue(result.categories.isEmpty()) },
        )
    }

    @Test
    fun `Kotlin 카테고리가 있는 Filter에서 Android 카테고리를 토글하면 둘 다 포함한다`() {
        // Given

        // When
        val result = filterWithCategory.toggleCategory(Category.Android)

        // Then
        assertAll(
            { assertTrue(result.categories.contains(Category.Kotlin)) },
            { assertTrue(result.categories.contains(Category.Android)) },
        )
    }

    @Test
    fun `풀이 완료 필터가 비활성화된 상태에서 토글하면 활성화된다`() {
        // Given

        // When
        val result = emptyFilter.toggleSolvedFilter()

        // Then
        assertTrue(result.showSolvedOnly)
    }

    @Test
    fun `풀이 완료 필터가 활성화된 상태에서 토글하면 비활성화된다`() {
        // Given

        // When
        val result = filterWithSolved.toggleSolvedFilter()

        // Then
        assertFalse(result.showSolvedOnly)
    }

    @Test
    fun `즐겨찾기 필터가 비활성화된 상태에서 토글하면 활성화된다`() {
        // Given

        // When
        val result = emptyFilter.toggleFavoritesFilter()

        // Then
        assertTrue(result.showFavoritesOnly)
    }

    @Test
    fun `즐겨찾기 필터가 활성화된 상태에서 토글하면 비활성화된다`() {
        // Given

        // When
        val result = filterWithFavorites.toggleFavoritesFilter()

        // Then
        assertFalse(result.showFavoritesOnly)
    }

    @Test
    fun `모든 옵션이 설정된 Filter는 비어있지 않다고 판단한다`() {
        // Given
        val filter =
            Filter(
                searchQuery = SearchQuery("kotlin"),
                categories = Categories(setOf(Category.Kotlin)),
                showSolvedOnly = true,
                showFavoritesOnly = true,
            )

        // When
        val result = filter.isEmpty()

        // Then
        assertFalse(result)
    }
}
