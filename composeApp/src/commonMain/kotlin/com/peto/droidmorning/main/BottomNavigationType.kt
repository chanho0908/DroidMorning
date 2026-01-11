package com.peto.droidmorning.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.bottom_nav_history
import droidmorning.composeapp.generated.resources.bottom_nav_profile
import droidmorning.composeapp.generated.resources.bottom_nav_question
import droidmorning.composeapp.generated.resources.bottom_nav_test
import org.jetbrains.compose.resources.StringResource

enum class BottomNavigationType(
    val icon: ImageVector,
    val label: StringResource,
) {
    QUESTION(
        icon = Icons.AutoMirrored.Outlined.MenuBook,
        label = Res.string.bottom_nav_question,
    ),
    TEST(
        icon = Icons.Filled.Description,
        label = Res.string.bottom_nav_test,
    ),
    HISTORY(
        icon = Icons.AutoMirrored.Filled.ListAlt,
        label = Res.string.bottom_nav_history,
    ),
    PROFILE(
        icon = Icons.Filled.Person,
        label = Res.string.bottom_nav_profile,
    ),
}
