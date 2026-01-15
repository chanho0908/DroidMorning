package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.assertAll
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CategoriesTest {
    private lateinit var emptyCategories: Categories
    private lateinit var categoriesWithKotlin: Categories
    private lateinit var categoriesWithAndroid: Categories
    private lateinit var categoriesWithMultiple: Categories

    @BeforeTest
    fun setup() {
        emptyCategories = Categories()
        categoriesWithKotlin = Categories(setOf(Category.Kotlin))
        categoriesWithAndroid = Categories(setOf(Category.Android))
        categoriesWithMultiple = Categories(setOf(Category.Kotlin, Category.Android))
    }

    @Test
    fun `빈 Categories는 비어있다고 판단한다`() {
        // Given

        // When
        val result = emptyCategories.isEmpty()

        // Then
        assertTrue(result)
    }

    @Test
    fun `항목이 있는 Categories는 비어있지 않다고 판단한다`() {
        // Given

        // When
        val result = categoriesWithKotlin.isEmpty()

        // Then
        assertFalse(result)
    }

    @Test
    fun `Kotlin이 포함된 Categories에서 Kotlin 포함 여부를 확인하면 true를 반환한다`() {
        // Given

        // When
        val result = categoriesWithKotlin.contains(Category.Kotlin)

        // Then
        assertTrue(result)
    }

    @Test
    fun `Kotlin이 없는 Categories에서 Kotlin 포함 여부를 확인하면 false를 반환한다`() {
        // Given

        // When
        val result = categoriesWithAndroid.contains(Category.Kotlin)

        // Then
        assertFalse(result)
    }

    @Test
    fun `빈 Categories에 Kotlin을 추가하면 Kotlin을 포함하게 된다`() {
        // Given

        // When
        val result = emptyCategories.add(Category.Kotlin)

        // Then
        assertAll(
            { assertTrue(result.contains(Category.Kotlin)) },
            { assertEquals(1, result.toSet().size) },
        )
    }

    @Test
    fun `Kotlin이 있는 Categories에 Android를 추가하면 둘 다 포함한다`() {
        // Given

        // When
        val result = categoriesWithKotlin.add(Category.Android)

        // Then
        assertAll(
            { assertTrue(result.contains(Category.Kotlin)) },
            { assertTrue(result.contains(Category.Android)) },
            { assertEquals(2, result.toSet().size) },
        )
    }

    @Test
    fun `Kotlin이 있는 Categories에서 Kotlin을 제거하면 비게 된다`() {
        // Given

        // When
        val result = categoriesWithKotlin.remove(Category.Kotlin)

        // Then
        assertAll(
            { assertFalse(result.contains(Category.Kotlin)) },
            { assertTrue(result.isEmpty()) },
        )
    }

    @Test
    fun `Kotlin과 Android가 있는 Categories에서 Kotlin을 제거하면 Android만 남는다`() {
        // Given

        // When
        val result = categoriesWithMultiple.remove(Category.Kotlin)

        // Then
        assertAll(
            { assertFalse(result.contains(Category.Kotlin)) },
            { assertTrue(result.contains(Category.Android)) },
            { assertEquals(1, result.toSet().size) },
        )
    }

    @Test
    fun `Categories를 Set으로 변환하면 올바른 Set을 반환한다`() {
        // Given
        val originalSet = setOf(Category.Kotlin, Category.Android, Category.Compose)
        val categories = Categories(originalSet)

        // When
        val result = categories.toSet()

        // Then
        assertEquals(originalSet, result)
    }

    @Test
    fun `이미 존재하는 카테고리를 다시 추가하면 단일 인스턴스로 유지된다`() {
        // Given

        // When
        val result = categoriesWithKotlin.add(Category.Kotlin)

        // Then
        assertTrue(result.contains(Category.Kotlin))
        assertEquals(1, result.toSet().size)
    }
}
