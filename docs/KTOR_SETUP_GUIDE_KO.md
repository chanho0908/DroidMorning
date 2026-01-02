# KMP(Kotlin Multiplatform) Ktor 네트워크 통신 설정 가이드 (한국어)

Kotlin Multiplatform 프로젝트에 Ktor 클라이언트를 사용한 네트워크 통신을 설정하는 완벽 가이드입니다.

## 목차
- [개요](#개요)
- [1단계: Ktor 의존성 추가](#1단계-ktor-의존성-추가)
- [2단계: HttpClient 설정](#2단계-httpclient-설정)
- [3단계: Koin DI 통합](#3단계-koin-di-통합)
- [4단계: API 서비스 구현](#4단계-api-서비스-구현)
- [5단계: 플랫폼별 동작 확인](#5단계-플랫폼별-동작-확인)
- [문제 해결](#문제-해결)
- [추가 팁](#추가-팁)

---

## 개요

### 사용 버전
- **Ktor**: 3.3.3
- **Kotlin**: 2.3.0
- **kotlinx-serialization**: 1.8.0
- **Koin**: 4.1.1

### 주요 기능
- ✅ **플랫폼별 엔진**: Android(OkHttp), iOS(Darwin)
- ✅ **JSON 직렬화**: kotlinx.serialization
- ✅ **Content Negotiation**: 자동 JSON 변환
- ✅ **로깅**: 요청/응답 로깅
- ✅ **Koin DI 통합**: 의존성 주입

### 프로젝트 구조
```
composeApp/
├── src/
│   ├── commonMain/kotlin/com/peto/droidmorning/
│   │   ├── data/
│   │   │   ├── network/
│   │   │   │   ├── HttpClientFactory.kt    # HttpClient 생성
│   │   │   │   └── ApiService.kt           # API 인터페이스
│   │   │   ├── model/
│   │   │   │   └── Response.kt             # 데이터 모델
│   │   │   └── di/
│   │   │       └── NetworkModule.kt        # Ktor DI 모듈
│   │   └── KoinInitializer.kt
│   ├── androidMain/
│   └── iosMain/
└── build.gradle.kts
```

---

## 1단계: Ktor 의존성 추가

### 1.1 Version Catalog 설정

`gradle/libs.versions.toml` 파일에 Ktor 및 Serialization 버전을 추가합니다.

```toml
[versions]
# ... 기존 버전들 ...

# kotlinX
kotlinx-coroutines = "1.10.2"
kotlinx-serialization = "1.8.0"

# ktor
ktor = "3.3.3"
```

### 1.2 라이브러리 정의

```toml
[libraries]
# ... 기존 라이브러리들 ...

# kotlinX
kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinx-coroutines" }
kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }

# ktor
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
```

### 1.3 플러그인 추가

```toml
[plugins]
# ... 기존 플러그인들 ...
kotlinxSerialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```

### 1.4 Build Gradle 설정

`composeApp/build.gradle.kts` 파일을 수정합니다.

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)  // 추가
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Android 전용 OkHttp 엔진
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        
        commonMain.dependencies {
            // Ktor 공통 의존성
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            
            // kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        
        iosMain.dependencies {
            // iOS 전용 Darwin 엔진
            implementation(libs.ktor.client.darwin)
        }
    }
}
```

---

## 2단계: HttpClient 설정

### 2.1 HttpClientFactory 생성

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/network/HttpClientFactory.kt`:

```kotlin
package com.peto.droidmorning.data.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientFactory {
    
    fun create(enableLogging: Boolean = true): HttpClient {
        return HttpClient {
            // JSON 직렬화 설정
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            
            // 로깅 설정
            if (enableLogging) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }
            
            // 기본 설정
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
            
            // 기본 헤더
            defaultRequest {
                url("https://api.example.com/")  // 기본 베이스 URL
                headers {
                    append("Content-Type", "application/json")
                }
            }
        }
    }
}
```

### 2.2 데이터 모델 정의

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/model/Response.kt`:

```kotlin
package com.peto.droidmorning.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String
)
```

**중요**: `@Serializable` 어노테이션을 반드시 추가해야 합니다.

---

## 3단계: Koin DI 통합

### 3.1 NetworkModule 생성

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/di/NetworkModule.kt`:

```kotlin
package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.network.HttpClientFactory
import com.peto.droidmorning.data.network.ApiService
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    // HttpClient 싱글톤 등록
    single<HttpClient> {
        HttpClientFactory.create(enableLogging = true)
    }
    
    // ApiService 싱글톤 등록
    single<ApiService> {
        ApiService(get())
    }
}
```

### 3.2 KoinInitializer에 추가

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/KoinInitializer.kt`:

```kotlin
package com.peto.droidmorning

import com.peto.droidmorning.data.di.networkModule  // 추가
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            networkModule,  // 추가
            // ... 다른 모듈들
        )
    }

// Common 초기화 (테스트용)
fun initKoin() = initKoin {}
```

---

## 4단계: API 서비스 구현

### 4.1 ApiService 클래스

`composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/network/ApiService.kt`:

```kotlin
package com.peto.droidmorning.data.network

import com.peto.droidmorning.data.model.ApiResponse
import com.peto.droidmorning.data.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(private val client: HttpClient) {
    
    // GET 요청 예시
    suspend fun getUsers(): Result<List<User>> = runCatching {
        val response: ApiResponse<List<User>> = client.get("users") {
            contentType(ContentType.Application.Json)
        }.body()
        
        response.data ?: emptyList()
    }
    
    // POST 요청 예시
    suspend fun createUser(user: User): Result<User> = runCatching {
        val response: ApiResponse<User> = client.post("users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
        
        response.data ?: throw Exception(response.message)
    }
    
    // PUT 요청 예시
    suspend fun updateUser(id: Int, user: User): Result<User> = runCatching {
        val response: ApiResponse<User> = client.put("users/$id") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
        
        response.data ?: throw Exception(response.message)
    }
    
    // DELETE 요청 예시
    suspend fun deleteUser(id: Int): Result<Boolean> = runCatching {
        val response: ApiResponse<Boolean> = client.delete("users/$id") {
            contentType(ContentType.Application.Json)
        }.body()
        
        response.success
    }
}
```

### 4.2 ViewModel에서 사용

```kotlin
package com.peto.droidmorning.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserViewModel : ViewModel(), KoinComponent {
    private val apiService: ApiService by inject()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            apiService.getUsers()
                .onSuccess { users ->
                    _users.value = users
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            
            _isLoading.value = false
        }
    }
}
```

---

## 5단계: 플랫폼별 동작 확인

### 5.1 Android 빌드

```bash
./gradlew :composeApp:assembleDebug
```

**Android 엔진**: OkHttp가 자동으로 사용됩니다.

### 5.2 iOS 빌드

```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

**iOS 엔진**: Darwin(NSURLSession)이 자동으로 사용됩니다.

### 5.3 동작 확인

#### Android 로그 확인
```bash
adb logcat | grep -i "ktor"
```

#### iOS 로그 확인
Xcode Console에서 Ktor 로그를 확인할 수 있습니다.

---

## 문제 해결

### 1. Unresolved reference: Serializable

**문제**: `@Serializable` 어노테이션을 찾을 수 없음

**해결방법**:
```kotlin
// 플러그인이 제대로 적용되었는지 확인
plugins {
    alias(libs.plugins.kotlinxSerialization)
}

// 의존성 확인
commonMain.dependencies {
    implementation(libs.kotlinx.serialization.json)
}
```

### 2. No suitable engine found

**문제**: HttpClient 생성 시 엔진을 찾을 수 없음

**해결방법**:
- **Android**: `implementation(libs.ktor.client.okhttp)` 확인
- **iOS**: `implementation(libs.ktor.client.darwin)` 확인

### 3. Content negotiation is not installed

**문제**: JSON 변환 시 에러 발생

**해결방법**:
```kotlin
// HttpClient 설정에 다음이 포함되었는지 확인
install(ContentNegotiation) {
    json()
}

// 의존성 확인
implementation(libs.ktor.client.content.negotiation)
implementation(libs.ktor.serialization.kotlinx.json)
```

### 4. Gradle Sync 실패

**해결방법**:
```bash
# Gradle 캐시 정리
./gradlew clean
rm -rf .gradle
rm -rf build

# 의존성 다시 다운로드
./gradlew --refresh-dependencies
```

---

## 추가 팁

### 1. 커스텀 JSON 설정

```kotlin
Json {
    prettyPrint = true           // 읽기 쉬운 JSON
    isLenient = true             // 완화된 파싱
    ignoreUnknownKeys = true     // 알 수 없는 키 무시
    coerceInputValues = true     // null을 기본값으로 변환
    encodeDefaults = false       // 기본값 인코딩 생략
}
```

### 2. 인증 헤더 추가

```kotlin
defaultRequest {
    headers {
        append("Authorization", "Bearer $token")
    }
}
```

### 3. 에러 핸들링

```kotlin
class ApiException(
    val statusCode: Int,
    override val message: String
) : Exception(message)

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> = runCatching {
    apiCall()
}.onFailure { exception ->
    when (exception) {
        is ClientRequestException -> {
            // 4xx 에러
            throw ApiException(
                exception.response.status.value,
                "클라이언트 에러: ${exception.message}"
            )
        }
        is ServerResponseException -> {
            // 5xx 에러
            throw ApiException(
                exception.response.status.value,
                "서버 에러: ${exception.message}"
            )
        }
        else -> {
            // 네트워크 에러 등
            throw exception
        }
    }
}
```

### 4. 재시도 로직

```kotlin
install(HttpRequestRetry) {
    retryOnServerErrors(maxRetries = 3)
    exponentialDelay()
}
```

### 5. 로깅 레벨 설정

```kotlin
install(Logging) {
    logger = Logger.DEFAULT
    level = when {
        isDebug -> LogLevel.ALL      // 개발 환경
        else -> LogLevel.NONE        // 프로덕션
    }
}
```

### 6. 멀티파트 파일 업로드

```kotlin
implementation(libs.ktor.client.multipart)

// 사용 예시
client.post("upload") {
    setBody(
        MultiPartFormDataContent(
            formData {
                append("file", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=\"image.jpg\"")
                })
            }
        )
    )
}
```

---

## 참고 자료

- [Ktor Client 공식 문서](https://ktor.io/docs/client.html)
- [kotlinx.serialization 가이드](https://github.com/Kotlin/kotlinx.serialization)
- [Koin 공식 문서](https://insert-koin.io/)
- [Kotlin Multiplatform 네트워킹](https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html)

---

## 완료! 🎉

이제 KMP 프로젝트에서 Ktor를 사용하여 네트워크 통신을 할 수 있습니다. Android와 iOS 모두에서 동일한 코드로 API를 호출할 수 있습니다.

**다음 단계 추천**:
1. 실제 API 엔드포인트로 베이스 URL 변경
2. 인증 토큰 관리 구현
3. 오프라인 캐싱 (SqlDelight 등 활용)
4. 에러 핸들링 강화
