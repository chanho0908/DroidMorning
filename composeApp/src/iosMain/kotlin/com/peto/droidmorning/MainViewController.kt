package com.peto.droidmorning

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        },
    ) { App() }
