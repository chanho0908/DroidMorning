package com.peto.droidmorning.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.auth.GoogleAuthClient
import com.peto.droidmorning.common.ObserveAsEvents
import com.peto.droidmorning.designsystem.component.GoogleSignInButton
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.app_name
import com.peto.droidmorning.designsystem.generated.resources.login_cancelled
import com.peto.droidmorning.designsystem.generated.resources.login_failed
import com.peto.droidmorning.designsystem.generated.resources.login_success
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.login.vm.AuthUiEvent
import com.peto.droidmorning.login.vm.LoginViewModel
import com.peto.droidmorning.login.vm.LoginViewModel.Companion.LOGIN_ENTRANCE_ANIMATION_DURATION
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.img_login_background
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    googleAuthClient: GoogleAuthClient = koinInject(),
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val loginSuccess = stringResource(DesignRes.string.login_success)
    val loginFailed = stringResource(DesignRes.string.login_failed)
    val loginCancelled = stringResource(DesignRes.string.login_cancelled)

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            AuthUiEvent.ShowLoginFailMessage -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(loginFailed)
                }
            }

            AuthUiEvent.ShowLoginCancelledMessage -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(loginCancelled)
                }
            }

            AuthUiEvent.NavigateToHomeScreen -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(loginSuccess)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        LoginContent(
            onGoogleLoginClick = {
                coroutineScope.launch {
                    val result = googleAuthClient.signIn()
                    viewModel.handleGoogleAuthResult(result)
                }
            },
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun LoginContent(
    onGoogleLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val offsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 100f,
        animationSpec = tween(durationMillis = LOGIN_ENTRANCE_ANIMATION_DURATION),
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = LOGIN_ENTRANCE_ANIMATION_DURATION),
    )

    Image(
        painter = painterResource(Res.drawable.img_login_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(Dimen.loginTitleTopSpacing))
        Text(
            text = stringResource(DesignRes.string.app_name),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.displayMedium,
            modifier =
                Modifier
                    .offset(y = offsetY.dp)
                    .alpha(alpha),
        )

        Spacer(modifier = Modifier.height(Dimen.loginButtonTopSpacing))

        GoogleSignInButton(onClick = onGoogleLoginClick)
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginContentPreview() {
    AppTheme {
        LoginContent(
            onGoogleLoginClick = {},
        )
    }
}
