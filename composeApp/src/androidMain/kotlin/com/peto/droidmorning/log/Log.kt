package com.peto.droidmorning.log

import android.util.Log

actual object Log {
    actual fun e(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        Log.e(tag, message, throwable)
    }

    actual fun d(
        tag: String,
        message: String,
    ) {
        Log.d(tag, message)
    }

    actual fun i(
        tag: String,
        message: String,
    ) {
        Log.i(tag, message)
    }
}
