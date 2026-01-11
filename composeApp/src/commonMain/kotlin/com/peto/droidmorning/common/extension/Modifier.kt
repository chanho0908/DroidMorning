package com.peto.droidmorning.common.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    then(
        Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) { onClick() },
    )
