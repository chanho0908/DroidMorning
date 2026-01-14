package com.peto.droidmorning.domain.model

data class SearchQuery(
    val value: String,
) {
    fun isEmpty(): Boolean = value.isBlank()
}
