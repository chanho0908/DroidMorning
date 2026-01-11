package com.peto.droidmorning.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.pretendard_black
import com.peto.droidmorning.designsystem.generated.resources.pretendard_bold
import com.peto.droidmorning.designsystem.generated.resources.pretendard_extrabold
import com.peto.droidmorning.designsystem.generated.resources.pretendard_extralight
import com.peto.droidmorning.designsystem.generated.resources.pretendard_light
import com.peto.droidmorning.designsystem.generated.resources.pretendard_medium
import com.peto.droidmorning.designsystem.generated.resources.pretendard_regular
import com.peto.droidmorning.designsystem.generated.resources.pretendard_semibold
import com.peto.droidmorning.designsystem.generated.resources.pretendard_thin
import org.jetbrains.compose.resources.Font

val droidMorningFontFamily: FontFamily
    @Composable
    get() =
        FontFamily(
            Font(DesignRes.font.pretendard_thin, FontWeight.Thin),
            Font(DesignRes.font.pretendard_extralight, FontWeight.ExtraLight),
            Font(DesignRes.font.pretendard_light, FontWeight.Light),
            Font(DesignRes.font.pretendard_regular, FontWeight.Normal),
            Font(DesignRes.font.pretendard_medium, FontWeight.Medium),
            Font(DesignRes.font.pretendard_semibold, FontWeight.SemiBold),
            Font(DesignRes.font.pretendard_bold, FontWeight.Bold),
            Font(DesignRes.font.pretendard_extrabold, FontWeight.ExtraBold),
            Font(DesignRes.font.pretendard_black, FontWeight.Black),
        )
