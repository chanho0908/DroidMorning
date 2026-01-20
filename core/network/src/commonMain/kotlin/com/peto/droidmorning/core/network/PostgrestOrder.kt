package com.peto.droidmorning.core.network

data class PostgrestOrder(
    val column: String,
    val descending: Boolean = false,
)
