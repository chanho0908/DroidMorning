package com.peto.droidmorning.domain.model

data class Categories(
    private val values: Set<Category> = emptySet(),
) {
    fun isEmpty(): Boolean = values.isEmpty()

    fun contains(category: Category): Boolean = values.contains(category)

    fun add(category: Category): Categories = Categories(values + category)

    fun remove(category: Category): Categories = Categories(values - category)

    fun toSet(): Set<Category> = values.toSet()
}
