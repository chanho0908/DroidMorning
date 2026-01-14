package com.peto.droidmorning.domain

fun assertAll(vararg assertions: () -> Unit) {
    val failures = mutableListOf<Throwable>()

    for (assertion in assertions) {
        try {
            assertion()
        } catch (e: Throwable) {
            failures.add(e)
        }
    }

    if (failures.isNotEmpty()) {
        val message =
            buildString {
                appendLine("Multiple assertions failed (${failures.size}):")
                failures.forEachIndexed { index, throwable ->
                    appendLine("${index + 1}. ${throwable.message}")
                }
            }
        throw AssertionError(message)
    }
}
