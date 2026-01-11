package com.peto.droidmorning.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    data object Initial : AuthState

    data object Loading : AuthState

    data object Success : AuthState

    data object Error : AuthState
}

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Initial)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            authRepository
                .signIn(idToken)
                .onSuccess { _uiState.value = AuthState.Success }
                .onFailure { _uiState.value = AuthState.Error }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository
                .signOut()
                .onSuccess { _uiState.value = AuthState.Initial }
                .onFailure { _uiState.value = AuthState.Error }
        }
    }
}
