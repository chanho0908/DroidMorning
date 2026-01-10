package com.peto.droidmorning.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes =
    Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(24.dp),
    )

object Shape {
    val categoryBadge = RoundedCornerShape(6.dp)

    // Button shapes
    val buttonSmall = RoundedCornerShape(8.dp)
    val buttonMedium = RoundedCornerShape(12.dp)
    val buttonLarge = RoundedCornerShape(16.dp)
    val buttonPill = RoundedCornerShape(50) // Fully rounded (Google style)

    val card = RoundedCornerShape(12.dp)
    val cardInteractive = RoundedCornerShape(12.dp)

    val inputField = RoundedCornerShape(12.dp)

    val bottomNav = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)

    val mobileFrame = RoundedCornerShape(40.dp)

    val logoContainer = RoundedCornerShape(24.dp)

    val featureItem = RoundedCornerShape(12.dp)
}
