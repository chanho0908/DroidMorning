package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.model.category.Category
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryTest {
    private lateinit var validCategoryName: String
    private lateinit var uppercaseCategoryName: String
    private lateinit var mixedCaseCategoryName: String

    @BeforeTest
    fun setup() {
        validCategoryName = "kotlin"
        uppercaseCategoryName = "ANDROID"
        mixedCaseCategoryName = "CoMpOsE"
    }

    @Test
    fun `유효한 카테고리 이름을 문자열로 변환하면 올바른 카테고리를 반환한다`() {
        // Given

        // When
        val result = Category.from(validCategoryName)

        // Then
        assertEquals(Category.Kotlin, result)
    }

    @Test
    fun `대문자 카테고리 이름을 변환하면 대소문자를 무시하고 올바른 카테고리를 반환한다`() {
        // Given

        // When
        val result = Category.from(uppercaseCategoryName)

        // Then
        assertEquals(Category.Android, result)
    }

    @Test
    fun `대소문자 혼합 카테고리 이름을 변환하면 올바른 카테고리를 반환한다`() {
        // Given

        // When
        val result = Category.from(mixedCaseCategoryName)

        // Then
        assertEquals(Category.Compose, result)
    }

    @Test
    fun `Coroutine 카테고리 이름을 변환하면 Coroutine 카테고리를 반환한다`() {
        // Given
        val categoryName = "coroutine"

        // When
        val result = Category.from(categoryName)

        // Then
        assertEquals(Category.Coroutine, result)
    }

    @Test
    fun `OOP 카테고리 이름을 변환하면 OOP 카테고리를 반환한다`() {
        // Given
        val categoryName = "oop"

        // When
        val result = Category.from(categoryName)

        // Then
        assertEquals(Category.OOP, result)
    }

    @Test
    fun `유효하지 않은 카테고리 이름을 변환하면 IllegalArgumentException을 발생시킨다`() {
        // Given
        val invalidCategoryName = "InvalidCategory"

        // When & Then
        val exception =
            assertFailsWith<IllegalArgumentException> {
                Category.from(invalidCategoryName)
            }
        assertEquals("Unknown category: InvalidCategory", exception.message)
    }

    @Test
    fun `빈 문자열을 변환하면 IllegalArgumentException을 발생시킨다`() {
        // Given
        val emptyCategoryName = ""

        // When & Then
        assertFailsWith<IllegalArgumentException> {
            Category.from(emptyCategoryName)
        }
    }
}
