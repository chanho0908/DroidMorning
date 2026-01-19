package com.peto.droidmorning.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CategoryCountResponse(
    val category: String,
    val count: Long,
)
