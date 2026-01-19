package com.peto.droidmorning.domain.model.category

enum class Category {
    Kotlin,
    Coroutine,
    Android,
    Compose,
    OOP,
    ;

    companion object {
        fun from(value: String): Category =
            entries
                .find { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("$UNKNOWN_CATEGORY_EXCEPTION $value")

        private const val UNKNOWN_CATEGORY_EXCEPTION = "Unknown category:"
    }
}
