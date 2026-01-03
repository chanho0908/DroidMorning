# Supabase 구글 로그인 구현 가이드

## 📋 목차
1. [Supabase 프로젝트 설정](#1-supabase-프로젝트-설정)
2. [Google OAuth 설정](#2-google-oauth-설정)
3. [환경 변수 설정](#3-환경-변수-설정)
4. [코드 구현](#4-코드-구현)
5. [테스트](#5-테스트)

---

## 1. Supabase 프로젝트 설정

### 1.1 Supabase 대시보드에서 OAuth 설정
1. [Supabase Dashboard](https://app.supabase.com/)에 접속
2. 프로젝트 선택 → **Authentication** → **Providers**로 이동
3. **Google** 프로바이더 찾아서 활성화

### 1.2 Callback URL 확인
- Supabase에서 제공하는 Callback URL 복사:
  ```
  https://<your-project-ref>.supabase.co/auth/v1/callback
  ```

---

## 2. Google OAuth 설정

### 2.1 Google Cloud Console 설정
1. [Google Cloud Console](https://console.cloud.google.com/) 접속
2. **APIs & Services** → **Credentials**로 이동
3. **+ CREATE CREDENTIALS** → **OAuth client ID** 선택

### 2.2 OAuth 동의 화면 구성 (처음 만드는 경우)
1. **OAuth consent screen** 탭 이동
2. User Type: **External** 선택 → **CREATE**
3. 앱 정보 입력:
   - 앱 이름: `DroidMorning`
   - 사용자 지원 이메일: 본인 이메일
   - 개발자 연락처 정보: 본인 이메일
4. 범위(Scopes) 설정:
   - `email` (기본 선택됨)
   - `profile` (기본 선택됨)

### 2.3 OAuth Client ID 생성

#### Android용 Client ID
1. Application type: **Android** 선택
2. 정보 입력:
   - Name: `DroidMorning Android`
   - Package name: `com.peto.droidmorning`
   - SHA-1 certificate fingerprint: 아래 명령어로 확인

```bash
# Debug 키스토어의 SHA-1 확인
keytool -keystore ~/.android/debug.keystore -list -v -alias androiddebugkey

# 기본 비밀번호: android
```

3. **CREATE** 클릭 → **Client ID** 저장

#### Web용 Client ID (필요시)
1. Application type: **Web application** 선택
2. 정보 입력:
   - Name: `DroidMorning Web`
   - Authorized redirect URIs: Supabase Callback URL 추가
     ```
     https://<your-project-ref>.supabase.co/auth/v1/callback
     ```
3. **CREATE** 클릭 → **Client ID**와 **Client Secret** 저장

### 2.4 Supabase에 Google OAuth 정보 등록
1. Supabase Dashboard → **Authentication** → **Providers** → **Google**
2. Google OAuth 정보 입력:
   - **Client ID**: Web용 Client ID
   - **Client Secret**: Web용 Client Secret
3. **Save** 클릭

---

## 3. 환경 변수 설정

### 3.1 local.properties 파일 생성
프로젝트 루트에 `local.properties` 파일 생성 (없으면):

```properties
# Google OAuth
GOOGLE_CLIENT_ID=<your-android-client-id>.apps.googleusercontent.com

# Supabase
SUPABASE_URL=https://<your-project-ref>.supabase.co
SUPABASE_KEY=<your-anon-key>
```

### 3.2 Supabase 정보 확인
- Supabase Dashboard → **Settings** → **API**에서:
  - **Project URL**: `SUPABASE_URL`
  - **anon public**: `SUPABASE_KEY`

⚠️ **중요**: `local.properties`는 `.gitignore`에 추가되어 있어야 합니다!

---

## 4. 코드 구현

### 4.1 AuthRepository 생성

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/repository/AuthRepository.kt` 생성:

```kotlin
package com.peto.droidmorning.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.user.UserInfo

interface AuthRepository {
    suspend fun signInWithGoogle(): Result<UserInfo>
    suspend fun signOut(): Result<Unit>
    fun getCurrentUser(): UserInfo?
}

class AuthRepositoryImpl(
    private val auth: Auth
) : AuthRepository {
    
    override suspend fun signInWithGoogle(): Result<UserInfo> {
        return try {
            auth.signInWith(Google)
            val user = auth.currentUserOrNull()
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to get user info"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getCurrentUser(): UserInfo? {
        return auth.currentUserOrNull()
    }
}
```

### 4.2 Android별 구현 (Credential Manager 사용)

`composeApp/src/androidMain/kotlin/com/peto/droidmorning/auth/GoogleAuthHelper.android.kt` 생성:

```kotlin
package com.peto.droidmorning.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.peto.droidmorning.BuildKonfig

class GoogleAuthHelper(private val context: Context) {
    
    suspend fun getGoogleIdToken(): String? {
        return try {
            val credentialManager = CredentialManager.create(context)
            
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildKonfig.GOOGLE_CLIENT_ID)
                .build()
            
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            googleIdTokenCredential.idToken
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
```

### 4.3 AuthViewModel 생성

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/presentation/viewmodel/AuthViewModel.kt` 생성:

```kotlin
package com.peto.droidmorning.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.data.repository.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    data object Initial : AuthState
    data object Loading : AuthState
    data class Success(val user: UserInfo) : AuthState
    data class Error(val message: String) : AuthState
}

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    init {
        checkCurrentUser()
    }
    
    private fun checkCurrentUser() {
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            _authState.value = AuthState.Success(currentUser)
        }
    }
    
    fun signInWithGoogle() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.signInWithGoogle()
                .onSuccess { user ->
                    _authState.value = AuthState.Success(user)
                }
                .onFailure { exception ->
                    _authState.value = AuthState.Error(
                        exception.message ?: "Login failed"
                    )
                }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
                .onSuccess {
                    _authState.value = AuthState.Initial
                }
                .onFailure { exception ->
                    _authState.value = AuthState.Error(
                        exception.message ?: "Logout failed"
                    )
                }
        }
    }
}
```

### 4.4 Koin DI 모듈 설정

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/di/AppModule.kt` 생성:

```kotlin
package com.peto.droidmorning.di

import com.peto.droidmorning.data.repository.AuthRepository
import com.peto.droidmorning.data.repository.AuthRepositoryImpl
import com.peto.droidmorning.presentation.viewmodel.AuthViewModel
import com.peto.droidmorning.supabaseClient
import io.github.jan.supabase.auth.auth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Supabase Auth
    single { supabaseClient.auth }
    
    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    
    // ViewModel
    viewModel { AuthViewModel(get()) }
}
```

### 4.5 App.kt에서 Koin 초기화

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/App.kt` 수정:

```kotlin
package com.peto.droidmorning

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.di.appModule
import com.peto.droidmorning.presentation.viewmodel.AuthState
import com.peto.droidmorning.presentation.viewmodel.AuthViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            AuthScreen()
        }
    }
}

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = authState) {
            is AuthState.Initial -> {
                Button(
                    onClick = { viewModel.signInWithGoogle() }
                ) {
                    Text("Sign in with Google")
                }
            }
            
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            
            is AuthState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Welcome!",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Email: ${state.user.email}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.signOut() }) {
                        Text("Sign Out")
                    }
                }
            }
            
            is AuthState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.signInWithGoogle() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}
```

### 4.6 Android MainActivity에서 Context 전달 (필요시)

Android에서 Credential Manager를 사용하려면 Activity Context가 필요합니다.

`composeApp/src/androidMain/kotlin/com/peto/droidmorning/MainActivity.kt`:

```kotlin
package com.peto.droidmorning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
```

---

## 5. 테스트

### 5.1 빌드 및 실행
```bash
# Android 빌드 및 실행
./gradlew :composeApp:installDebug
```

### 5.2 동작 확인
1. 앱 실행
2. **"Sign in with Google"** 버튼 클릭
3. Google 계정 선택 화면이 나타남
4. 계정 선택 후 로그인 성공
5. 사용자 이메일이 화면에 표시됨
6. **"Sign Out"** 버튼으로 로그아웃

### 5.3 문제 해결

#### "No matching client found for package name"
- Google Cloud Console에서 Package name과 SHA-1이 올바르게 설정되었는지 확인
- `local.properties`의 `GOOGLE_CLIENT_ID`가 Android Client ID인지 확인

#### "Invalid redirect URI"
- Supabase Dashboard의 Callback URL이 Google OAuth 설정에 추가되었는지 확인

#### "Failed to get user info"
- Supabase Dashboard → Authentication → Providers → Google이 활성화되었는지 확인
- Client ID와 Client Secret이 올바르게 입력되었는지 확인

---

## 📚 참고 자료

- [Supabase Auth Documentation](https://supabase.com/docs/guides/auth)
- [Supabase Google OAuth Guide](https://supabase.com/docs/guides/auth/social-login/auth-google)
- [Google Sign-In for Android](https://developers.google.com/identity/sign-in/android/start-integrating)
- [Android Credential Manager](https://developer.android.com/training/sign-in/credential-manager)
- [Supabase Kotlin Library](https://github.com/supabase-community/supabase-kt)

---

## ⚡ 다음 단계

1. **iOS 구현**: iOS용 Google 로그인 구현
2. **토큰 저장**: 로그인 세션 유지를 위한 토큰 저장
3. **에러 핸들링**: 더 자세한 에러 메시지 및 처리
4. **UI/UX 개선**: 로딩 상태, 애니메이션 추가
5. **테스트 코드**: 단위 테스트 및 통합 테스트 작성
