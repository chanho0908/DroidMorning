package com.peto.droidmorning

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform