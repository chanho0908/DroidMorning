package com.peto.droidmorning.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
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

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        authRepository
            .getCurrentUser()
            .onSuccess {
                when (it) {
                    is AuthType.Authenticated -> {
                        _authState.value = AuthState.Success
                    }

                    is AuthType.Unauthenticated -> {
                        _authState.value = AuthState.Initial
                    }
                }
            }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository
                .signInWithGoogle(idToken)
                .onSuccess { user -> _authState.value = AuthState.Success(user) }
                .onFailure { _authState.value = AuthState.Error }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository
                .signOut()
                .onSuccess { _authState.value = AuthState.Initial }
                .onFailure { _authState.value = AuthState.Error }
        }
    }
}
