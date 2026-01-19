package com.peto.droidmorning.domain.model

import com.peto.droidmorning.domain.model.category.Categories
import com.peto.droidmorning.domain.model.category.Category

data class Filter(
    val searchQuery: SearchQuery = SearchQuery(""),
    val categories: Categories = Categories(emptySet()),
    val solved: Boolean = false,
    val liked: Boolean = false,
) {
    fun isEmpty(): Boolean =
        searchQuery.isEmpty() &&
            categories.isEmpty() &&
            !solved &&
            !liked

    fun applySearchQuery(query: String): Filter = copy(searchQuery = SearchQuery(query))

    fun addCategory(category: Category): Filter = copy(categories = categories.add(category))

    fun removeCategory(category: Category): Filter = copy(categories = categories.remove(category))

    fun applySolvedFilter(): Filter = copy(solved = true)

    fun clearSolvedFilter(): Filter = copy(solved = false)

    fun applyLikedFilter(): Filter = copy(liked = true)

    fun clearLikedFilter(): Filter = copy(liked = false)
}
