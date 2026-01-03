package com.peto.droidmorning

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.auth.GoogleAuthClient
import com.peto.droidmorning.auth.GoogleAuthResult
import com.peto.droidmorning.presentation.vm.AuthState
import com.peto.droidmorning.presentation.vm.AuthViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        AuthScreen()
    }
}

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    googleAuthClient: GoogleAuthClient = koinInject(),
) {
    val authState by viewModel.authState.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (val state = authState) {
            is AuthState.Initial -> {
                Button(
                    onClick = {
                        scope.launch {
                            when (val result = googleAuthClient.signIn()) {
                                is GoogleAuthResult.Success -> {
                                    viewModel.signInWithGoogle(result.idToken)
                                }
                                is GoogleAuthResult.Failure -> {
                                    errorMessage = result.message
                                }
                                GoogleAuthResult.Cancelled -> {
                                    errorMessage = "로그인이 취소되었습니다"
                                }
                            }
                        }
                    },
                ) {
                    Text("Google로 로그인")
                }

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }

            is AuthState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("환영합니다!", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.signOut() }) {
                        Text("로그아웃")
                    }
                }
            }

            is AuthState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("로그인에 실패했습니다.", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
