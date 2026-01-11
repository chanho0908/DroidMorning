package com.peto.droidmorning.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.auth.GoogleAuthResult
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AuthUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthType()
    }

    private fun checkAuthType() {
        viewModelScope.launch {
            when (authRepository.authType()) {
                AuthType.Authenticated -> {
                    delay(LOGIN_ENTRANCE_ANIMATION_DURATION.toLong())
                    sendUiEvent(AuthUiEvent.NavigateToHomeScreen)
                }

                AuthType.Unauthenticated -> {
                    _uiState.update { it.copy(showLoginButton = true) }
                }
            }
        }
    }

    fun handleGoogleAuthResult(result: GoogleAuthResult) {
        when (result) {
            is GoogleAuthResult.Success -> signInWithGoogle(result.idToken)
            is GoogleAuthResult.Failure -> sendUiEvent(AuthUiEvent.ShowLoginFailMessage)
            is GoogleAuthResult.Cancelled -> sendUiEvent(AuthUiEvent.ShowLoginCancelledMessage)
        }
    }

    private fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository
                .signIn(idToken)
                .onSuccess {
                    sendUiEvent(AuthUiEvent.NavigateToHomeScreen)
                }.onFailure { sendUiEvent(AuthUiEvent.ShowLoginFailMessage) }
        }
    }

    private fun sendUiEvent(event: AuthUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    companion object {
        const val LOGIN_ENTRANCE_ANIMATION_DURATION = 1000
    }
}
