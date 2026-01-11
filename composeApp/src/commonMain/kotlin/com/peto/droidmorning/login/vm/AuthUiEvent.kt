package com.peto.droidmorning.login.vm

sealed interface AuthUiEvent {
    data object NavigateToHomeScreen : AuthUiEvent

    data object ShowLoginFailMessage : AuthUiEvent

    data object ShowLoginCancelledMessage : AuthUiEvent
}
