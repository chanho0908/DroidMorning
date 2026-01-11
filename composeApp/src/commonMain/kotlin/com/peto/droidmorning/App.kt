package com.peto.droidmorning

import androidx.compose.runtime.Composable
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.navigation.AppNavHost

@Composable
fun App() {
    AppTheme {
        AppNavHost()
    }
}
