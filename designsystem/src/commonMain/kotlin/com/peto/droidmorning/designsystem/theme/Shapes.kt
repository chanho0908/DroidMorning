package com.peto.droidmorning.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val DroidMorningShapes =
    Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(24.dp),
    )

val CategoryBadge = RoundedCornerShape(6.dp)

val ButtonSmall = RoundedCornerShape(8.dp)
val ButtonMedium = RoundedCornerShape(12.dp)
val ButtonLarge = RoundedCornerShape(16.dp)
val ButtonPill = RoundedCornerShape(50)

val Card = RoundedCornerShape(12.dp)
val CardInteractive = RoundedCornerShape(12.dp)

val InputField = RoundedCornerShape(12.dp)

val BottomNav = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)

val MobileFrame = RoundedCornerShape(40.dp)

val LogoContainer = RoundedCornerShape(24.dp)

val FeatureItem = RoundedCornerShape(12.dp)
