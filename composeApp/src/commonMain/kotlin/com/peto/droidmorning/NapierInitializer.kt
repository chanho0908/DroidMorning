package com.peto.droidmorning

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initNapier() {
    Napier.base(DebugAntilog())
}
