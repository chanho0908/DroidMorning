package com.peto.droidmorning.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.presentation.vm.AuthState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    authState: AuthState,
    errorMessage: String?,
    onGoogleLoginClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (authState) {
            is AuthState.Initial -> {
                Text("DroidMorning", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Text("구글로 간편 로그인", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = onGoogleLoginClick) {
                    Text("Google로 로그인")
                }

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }

            is AuthState.Loading -> CircularProgressIndicator()

            is AuthState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("환영합니다!", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onSignOutClick) {
                        Text("로그아웃")
                    }
                }
            }

            is AuthState.Error -> {
                Text("로그인에 실패했습니다.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            authState = AuthState.Initial,
            errorMessage = null,
            onGoogleLoginClick = {},
            onSignOutClick = {},
        )
    }
}
