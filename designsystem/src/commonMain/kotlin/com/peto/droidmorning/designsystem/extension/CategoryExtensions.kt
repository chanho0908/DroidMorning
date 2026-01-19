package com.peto.droidmorning.designsystem.extension

import androidx.compose.ui.graphics.Color
import com.peto.droidmorning.designsystem.theme.CategoryAndroid
import com.peto.droidmorning.designsystem.theme.CategoryCompose
import com.peto.droidmorning.designsystem.theme.CategoryCoroutine
import com.peto.droidmorning.designsystem.theme.CategoryKotlin
import com.peto.droidmorning.designsystem.theme.CategoryOOP
import com.peto.droidmorning.domain.model.category.Category

val Category.color: Color
    get() =
        when (this) {
            Category.Kotlin -> CategoryKotlin
            Category.Compose -> CategoryCompose
            Category.Coroutine -> CategoryCoroutine
            Category.Android -> CategoryAndroid
            Category.OOP -> CategoryOOP
        }
