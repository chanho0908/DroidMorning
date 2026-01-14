package com.peto.droidmorning.domain.model

data class Filter(
    val searchQuery: SearchQuery = SearchQuery(""),
    val categories: Categories = Categories(),
    val showSolvedOnly: Boolean = false,
    val showFavoritesOnly: Boolean = false,
) {
    fun isEmpty(): Boolean =
        searchQuery.isEmpty() &&
            categories.isEmpty() &&
            !showSolvedOnly &&
            !showFavoritesOnly

    fun updateSearchQuery(query: String): Filter = copy(searchQuery = SearchQuery(query))

    fun toggleCategory(category: Category): Filter =
        copy(
            categories =
                if (categories.contains(category)) {
                    categories.remove(category)
                } else {
                    categories.add(category)
                },
        )

    fun toggleSolvedFilter(): Filter = copy(showSolvedOnly = !showSolvedOnly)

    fun toggleFavoritesFilter(): Filter = copy(showFavoritesOnly = !showFavoritesOnly)
}
