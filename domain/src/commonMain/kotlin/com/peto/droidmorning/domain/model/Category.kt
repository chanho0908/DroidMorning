package com.peto.droidmorning.domain.model

enum class Category {
    Kotlin,
    Coroutine,
    Android,
    Compose,
    Unknown,
    ;

    companion object {
        fun from(value: String): Category = entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: Unknown
    }
}
