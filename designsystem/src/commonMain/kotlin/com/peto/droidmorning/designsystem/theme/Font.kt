package com.peto.droidmorning.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import droidmorning.designsystem.generated.resources.Res
import droidmorning.designsystem.generated.resources.pretendard_black
import droidmorning.designsystem.generated.resources.pretendard_bold
import droidmorning.designsystem.generated.resources.pretendard_extrabold
import droidmorning.designsystem.generated.resources.pretendard_extralight
import droidmorning.designsystem.generated.resources.pretendard_light
import droidmorning.designsystem.generated.resources.pretendard_medium
import droidmorning.designsystem.generated.resources.pretendard_regular
import droidmorning.designsystem.generated.resources.pretendard_semibold
import droidmorning.designsystem.generated.resources.pretendard_thin
import org.jetbrains.compose.resources.Font

val droidMorningFontFamily: FontFamily
    @Composable
    get() =
        FontFamily(
            Font(Res.font.pretendard_thin, FontWeight.Thin),
            Font(Res.font.pretendard_extralight, FontWeight.ExtraLight),
            Font(Res.font.pretendard_light, FontWeight.Light),
            Font(Res.font.pretendard_regular, FontWeight.Normal),
            Font(Res.font.pretendard_medium, FontWeight.Medium),
            Font(Res.font.pretendard_semibold, FontWeight.SemiBold),
            Font(Res.font.pretendard_bold, FontWeight.Bold),
            Font(Res.font.pretendard_extrabold, FontWeight.ExtraBold),
            Font(Res.font.pretendard_black, FontWeight.Black),
        )
