package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.assertAll
import com.peto.droidmorning.domain.model.category.Categories
import com.peto.droidmorning.domain.model.category.Category
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
        filterWithSolved = Filter(solved = true)
        filterWithFavorites = Filter(liked = true)
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
        val result = emptyFilter.applySearchQuery(newQuery)

        // Then
        assertAll(
            { assertEquals(newQuery, result.searchQuery.value) },
            { assertEquals(emptyFilter.categories, result.categories) },
            { assertEquals(emptyFilter.solved, result.solved) },
            { assertEquals(emptyFilter.liked, result.liked) },
        )
    }

    @Test
    fun `카테고리가 없는 Filter에 카테고리를 추가하면 해당 카테고리가 포함된다`() {
        // Given

        // When
        val result = emptyFilter.addCategory(Category.Kotlin)

        // Then
        assertTrue(result.categories.contains(Category.Kotlin))
    }

    @Test
    fun `카테고리가 있는 Filter에서 해당 카테고리를 제거하면 비게 된다`() {
        // Given

        // When
        val result = filterWithCategory.removeCategory(Category.Kotlin)

        // Then
        assertAll(
            { assertFalse(result.categories.contains(Category.Kotlin)) },
            { assertTrue(result.categories.isEmpty()) },
        )
    }

    @Test
    fun `Kotlin 카테고리가 있는 Filter에 Android 카테고리를 추가하면 둘 다 포함한다`() {
        // Given

        // When
        val result = filterWithCategory.addCategory(Category.Android)

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
        val result = emptyFilter.applySolvedFilter()

        // Then
        assertTrue(result.solved)
    }

    @Test
    fun `풀이 완료 필터가 활성화된 상태에서 clear하면 비활성화된다`() {
        // Given

        // When
        val result = filterWithSolved.clearSolvedFilter()

        // Then
        assertFalse(result.solved)
    }

    @Test
    fun `즐겨찾기 필터가 비활성화된 상태에서 apply하면 활성화된다`() {
        // Given

        // When
        val result = emptyFilter.applyLikedFilter()

        // Then
        assertTrue(result.liked)
    }

    @Test
    fun `즐겨찾기 필터가 활성화된 상태에서 clear하면 비활성화된다`() {
        // Given

        // When
        val result = filterWithFavorites.clearLikedFilter()

        // Then
        assertFalse(result.liked)
    }

    @Test
    fun `모든 옵션이 설정된 Filter는 비어있지 않다고 판단한다`() {
        // Given
        val filter =
            Filter(
                searchQuery = SearchQuery("kotlin"),
                categories = Categories(setOf(Category.Kotlin)),
                solved = true,
                liked = true,
            )

        // When
        val result = filter.isEmpty()

        // Then
        assertFalse(result)
    }
}
