package com.peto.droidmorning.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    data object Initial : AuthState

    data object Loading : AuthState

    data class Success(
        val user: UserInfo,
    ) : AuthState

    data class Error(
        val message: String,
    ) : AuthState
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
                        _authState.value = AuthState.Success(it.userInfo)
                    }

                    is AuthType.Unauthenticated -> {
                        // _authState.value = AuthState.Error(it)
                    }
                }
            }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository
                .signInWithGoogle(idToken)
                .onSuccess { user ->
                    _authState.value = AuthState.Success(user)
                }.onFailure { exception ->
                    _authState.value =
                        AuthState.Error(
                            exception.message ?: "로그인에 실패했어요",
                        )
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository
                .signOut()
                .onSuccess {
                    _authState.value = AuthState.Initial
                }.onFailure { exception ->
                    _authState.value =
                        AuthState.Error(
                            exception.message ?: "로그아웃에 실패했어요",
                        )
                }
        }
    }
}
