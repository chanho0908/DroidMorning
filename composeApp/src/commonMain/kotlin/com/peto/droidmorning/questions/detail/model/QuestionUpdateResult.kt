package com.peto.droidmorning.questions.detail.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionUpdateResult(
    val isLiked: Boolean,
    val isSolved: Boolean,
)
