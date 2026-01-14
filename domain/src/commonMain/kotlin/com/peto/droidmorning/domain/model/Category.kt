package com.peto.droidmorning.domain.model

enum class Category {
    Kotlin,
    Coroutine,
    Android,
    Compose,
    OOP,
    ;

    companion object {
        fun name(value: String): Category =
            entries
                .firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown category: $value")
    }
}
