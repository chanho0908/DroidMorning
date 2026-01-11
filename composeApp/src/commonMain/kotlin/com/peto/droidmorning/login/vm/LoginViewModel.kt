package com.peto.droidmorning.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiEvent = Channel<AuthUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthType()
    }

    private fun checkAuthType() {
        viewModelScope.launch {
            when (authRepository.authType()) {
                AuthType.Authenticated -> sendUiEvent(AuthUiEvent.NavigateToHomeScreen)
                AuthType.Unauthenticated -> Unit
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository
                .signIn(idToken)
                .onSuccess {
                    delay(1000)
                    sendUiEvent(AuthUiEvent.NavigateToHomeScreen)
                }.onFailure { sendUiEvent(AuthUiEvent.ShowLoginFailMessage) }
        }
    }

    fun sendUiEvent(event: AuthUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    companion object {
        const val NAVIGATE_TO_HOME_SCREEN_DURATION = 1000
    }
}
