# KMP(Kotlin Multiplatform) Koin DI 적용 가이드 (한국어)

Kotlin Multiplatform 프로젝트에 Koin Dependency Injection을 설정하는 완벽 가이드입니다.

## 목차
- [개요](#개요)
- [1단계: Koin 의존성 추가](#1단계-koin-의존성-추가)
- [2단계: DI 모듈 구성](#2단계-di-모듈-구성)
- [3단계: Android 플랫폼 설정](#3단계-android-플랫폼-설정)
- [4단계: iOS 플랫폼 설정](#4단계-ios-플랫폼-설정)
- [5단계: 프레임워크 빌드 및 검증](#5단계-프레임워크-빌드-및-검증)
- [문제 해결](#문제-해결)
- [추가 팁](#추가-팁)

---

## 개요

### 사용 버전
- **Koin**: 4.1.1
- **Kotlin**: 2.3.0
- **Compose Multiplatform**: 1.9.3

### 프로젝트 구조
```
composeApp/
├── src/
│   ├── commonMain/kotlin/com/peto/droidmorning/
│   │   ├── KoinInitializer.kt           # Koin 초기화 함수
│   │   ├── data/di/
│   │   │   ├── NetworkModule.kt         # 네트워크 관련 DI
│   │   │   └── RepositoryModule.kt      # Repository DI
│   │   └── ui/di/
│   │       └── ViewModelModule.kt       # ViewModel DI
│   ├── androidMain/kotlin/com/peto/droidmorning/
│   │   └── DroidMorningApp.kt           # Android Application
│   └── iosMain/
└── build.gradle.kts

iosApp/
└── iosApp/
    └── iOSApp.swift                      # iOS App Entry Point
```

---

## 1단계: Koin 의존성 추가

### 1.1 Version Catalog 설정

`gradle/libs.versions.toml` 파일을 열어 Koin 버전과 라이브러리를 추가합니다.

```toml
[versions]
# 기존 버전들...
kotlin = "2.3.0"
composeMultiplatform = "1.9.3"

# Koin 버전 추가
koin = "4.1.1"

[libraries]
# 기존 라이브러리들...

# Koin 라이브러리 추가
koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
koin-compose-viewmodel = { module = "io.insert-koin:koin-compose-viewmodel", version.ref = "koin" }
koin-test = { module = "io.insert-koin:koin-test", version.ref = "koin" }
```

### 1.2 composeApp의 build.gradle.kts 수정

`composeApp/build.gradle.kts` 파일의 의존성 섹션에 Koin을 추가합니다.

```kotlin
kotlin {
    // ... 기존 설정 (androidTarget, iOS 타겟 등)
    
    sourceSets {
        commonMain.dependencies {
            // Compose 의존성들...
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            
            // Koin 의존성 추가
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)  // 테스트용
        }
    }
}
```

### 1.3 Gradle 동기화

Android Studio에서 **Sync Project with Gradle Files** 버튼을 클릭하거나, 터미널에서 실행합니다:

```bash
./gradlew clean build
```

---

## 2단계: DI 모듈 구성

### 2.1 KoinInitializer 생성

공통 모듈에서 사용할 Koin 초기화 함수를 만듭니다.

**파일 경로**: `composeApp/src/commonMain/kotlin/com/peto/droidmorning/KoinInitializer.kt`

```kotlin
package com.peto.droidmorning

import com.peto.droidmorning.data.di.networkModule
import com.peto.droidmorning.data.di.repositoryModule
import com.peto.droidmorning.ui.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    extraModules: List<Module> = emptyList(),
    declaration: KoinAppDeclaration = {},
) {
    startKoin {
        declaration()
        
        modules(
            extraModules +
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                ),
        )
    }
}
```

**핵심 개념:**
- `extraModules`: 각 플랫폼에서 추가 모듈을 주입할 수 있습니다
- `declaration`: Android Context나 Logger 같은 플랫폼별 설정을 추가할 수 있습니다
- 모든 공통 모듈을 하나의 리스트로 관리합니다

### 2.2 네트워크 모듈 생성

**파일 경로**: `composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/di/NetworkModule.kt`

```kotlin
package com.peto.droidmorning.data.di

import org.koin.dsl.module

val networkModule = module {
    // 예시: HttpClient, API Service 등을 여기에 추가
    // single { createHttpClient() }
    // single { ApiService(get()) }
}
```

**나중에 추가할 예시:**
```kotlin
val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    
    single { 
        ApiService(get()) 
    }
}
```

### 2.3 Repository 모듈 생성

**파일 경로**: `composeApp/src/commonMain/kotlin/com/peto/droidmorning/data/di/RepositoryModule.kt`

```kotlin
package com.peto.droidmorning.data.di

import org.koin.dsl.module

val repositoryModule = module {
    // 예시: Repository 구현체
    // single<UserRepository> { UserRepositoryImpl(get()) }
}
```

**나중에 추가할 예시:**
```kotlin
val repositoryModule = module {
    single<UserRepository> { 
        UserRepositoryImpl(
            apiService = get(),
            database = get()
        ) 
    }
    
    single<PostRepository> { 
        PostRepositoryImpl(get()) 
    }
}
```

### 2.4 ViewModel 모듈 생성

**파일 경로**: `composeApp/src/commonMain/kotlin/com/peto/droidmorning/ui/di/ViewModelModule.kt`

```kotlin
package com.peto.droidmorning.ui.di

import org.koin.dsl.module

val viewModelModule = module {
    // 예시: ViewModel 등록
    // viewModel { MainViewModel(get()) }
}
```

**나중에 추가할 예시:**
```kotlin
val viewModelModule = module {
    viewModel { 
        MainViewModel(
            userRepository = get()
        ) 
    }
    
    viewModel { 
        ProfileViewModel(get(), get()) 
    }
}
```

---

## 3단계: Android 플랫폼 설정

### 3.1 Application 클래스 생성

Android에서 앱 시작 시 Koin을 초기화하기 위한 Application 클래스를 만듭니다.

**파일 경로**: `composeApp/src/androidMain/kotlin/com/peto/droidmorning/DroidMorningApp.kt`

```kotlin
package com.peto.droidmorning

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class DroidMorningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        initKoin {
            androidContext(this@DroidMorningApp)
            androidLogger(Level.DEBUG)  // 개발 중에만 사용, 릴리즈에서는 제거
        }
    }
}
```

**코드 설명:**
- `androidContext()`: Android Context를 Koin에 제공 (Activity, Service 등에서 Context가 필요한 경우 사용)
- `androidLogger(Level.DEBUG)`: Koin의 로그를 Logcat에 출력 (디버깅용)

**릴리즈 버전에서는:**
```kotlin
initKoin {
    androidContext(this@DroidMorningApp)
    if (BuildConfig.DEBUG) {
        androidLogger(Level.DEBUG)
    }
}
```

### 3.2 AndroidManifest.xml 설정

Application 클래스를 시스템에 등록해야 합니다.

**파일 경로**: `composeApp/src/androidMain/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:name=".DroidMorningApp"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar">
        
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

**중요**: `android:name=".DroidMorningApp"` 속성을 꼭 추가하세요!

---

## 4단계: iOS 플랫폼 설정

### 4.1 iOS App에서 Koin 초기화

iOS 앱의 시작점에서 Koin을 초기화합니다.

**파일 경로**: `iosApp/iosApp/iOSApp.swift`

```swift
import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinInitializerKt.doInitKoin(extraModules: [], declaration: { _ in })
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

### ⚠️ 매우 중요: Swift 키워드 충돌

**왜 `initKoin`이 아니라 `doInitKoin`을 사용하나요?**

Swift에서 `init`은 초기화 메서드를 위한 **예약 키워드**입니다. 따라서 Kotlin/Native 컴파일러가 Kotlin 함수를 Objective-C/Swift로 변환할 때, `init`으로 시작하는 함수명에 자동으로 `do` 접두사를 추가합니다.

**변환 규칙:**
- Kotlin: `fun initKoin()` → Swift: `KoinInitializerKt.doInitKoin()`
- Kotlin: `fun initDatabase()` → Swift: `doInitDatabase()`

#### 대안 1: @ObjCName 사용 (권장하지 않음)

Kotlin 코드에서 명시적으로 이름을 지정할 수 있지만, 권장하지 않습니다:

```kotlin
@ObjCName("setupKoin")  // Swift에서 setupKoin()으로 사용 가능
fun initKoin(
    extraModules: List<Module> = emptyList(),
    declaration: KoinAppDeclaration = {},
) {
    // ...
}
```

#### 대안 2: 함수명 변경

아예 Kotlin에서 함수명을 바꾸는 것도 방법입니다:

```kotlin
// Kotlin
fun setupKoin(...) { ... }

// Swift
KoinInitializerKt.setupKoin(extraModules: [], declaration: { _ in })
```

**하지만 `doInitKoin`을 그대로 사용하는 것을 권장합니다!**

---

## 5단계: 프레임워크 빌드 및 검증

### 5.1 iOS 프레임워크 빌드

Kotlin 코드를 변경했으므로, iOS에서 사용할 프레임워크를 다시 빌드해야 합니다.

**시뮬레이터용 빌드:**
```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

**물리 기기용 빌드:**
```bash
./gradlew :composeApp:linkDebugFrameworkIosArm64
```

**두 가지 모두 빌드:**
```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64 \
          :composeApp:linkDebugFrameworkIosArm64
```

### 5.2 Xcode 캐시 완전 삭제

가끔 Xcode가 오래된 프레임워크를 캐시하고 있어서 문제가 생깁니다. 이를 해결하려면:

```bash
# Xcode Derived Data 삭제
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*

# 또는 모든 Xcode 캐시 삭제 (권장)
rm -rf ~/Library/Developer/Xcode/DerivedData/*
```

### 5.3 Xcode에서 Clean Build

1. Xcode를 엽니다
2. **Product → Clean Build Folder** (단축키: `Cmd + Shift + K`)
3. **Product → Build** (단축키: `Cmd + B`)

### 5.4 빌드 성공 확인

빌드가 성공하면 다음과 같은 메시지가 나타납니다:
```
Build Succeeded
```

앱을 실행해보세요! (단축키: `Cmd + R`)

---

## 문제 해결

### 문제 1: "Could not build Objective-C module 'ComposeApp'"

**증상:**
```
error: Could not build Objective-C module 'ComposeApp'
```

**원인:**
iOS 프레임워크가 최신 Kotlin 코드로 빌드되지 않았습니다.

**해결 방법:**

**단계 1**: Gradle 클린 및 프레임워크 재빌드
```bash
./gradlew :composeApp:clean
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

**단계 2**: Xcode 캐시 삭제
```bash
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*
```

**단계 3**: Xcode에서 Clean Build
- Product → Clean Build Folder (`Cmd + Shift + K`)
- Product → Build (`Cmd + B`)

### 문제 2: "Type 'KoinInitializerKt' has no member 'initKoin'"

**증상:**
```swift
KoinInitializerKt.initKoin()  // 에러: 'initKoin' 멤버를 찾을 수 없음
```

**원인:**
Swift의 `init` 키워드 충돌로 함수명이 자동으로 변경되었습니다.

**해결 방법:**

Swift 코드에서 `doInitKoin`을 사용하세요:

```swift
// 잘못된 코드
KoinInitializerKt.initKoin()

// 올바른 코드
KoinInitializerKt.doInitKoin(extraModules: [], declaration: { _ in })
```

### 문제 3: Multiple Koin instances detected

**증상:**
```
org.koin.core.error.KoinAppAlreadyStartedException: 
A Koin Application has already been started
```

**원인:**
Koin이 여러 번 초기화되었습니다 (앱 재시작 등).

**해결 방법 1**: 기존 인스턴스 정리 후 재시작

```kotlin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun initKoin(...) {
    stopKoin()  // 기존 인스턴스 정리
    startKoin {
        // ... 설정
    }
}
```

**해결 방법 2**: 이미 시작되었는지 확인

```kotlin
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun initKoin(...) {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            // ... 설정
        }
    }
}
```

### 문제 4: Cannot infer a bundle ID

**증상:**
```
warning: Cannot infer a bundle ID from packages of source files 
and exported dependencies, use the bundle name instead: ComposeApp
```

**원인:**
iOS 프레임워크의 Bundle ID가 명시되지 않았습니다.

**해결 방법:**

`composeApp/build.gradle.kts` 파일을 수정합니다:

```kotlin
kotlin {
    // ... 기존 설정
    
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            
            // Bundle ID 명시
            binaryOption("bundleId", "com.peto.droidmorning.ComposeApp")
        }
    }
}
```

### 문제 5: No definition found for...

**증상:**
```
org.koin.core.error.NoBeanDefFoundException: 
No definition found for class:'UserRepository'
```

**원인:**
해당 타입이 Koin 모듈에 등록되지 않았습니다.

**해결 방법:**

의존성을 모듈에 추가하세요:

```kotlin
val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}
```

---

## 추가 팁

### 팁 1: 모듈 분리 전략

프로젝트가 커지면 다음과 같이 모듈을 세분화하세요:

```kotlin
// data/di/NetworkModule.kt
val networkModule = module {
    // HttpClient 설정
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
    
    // API 서비스들
    single { UserApiService(get()) }
    single { PostApiService(get()) }
}

// data/di/RepositoryModule.kt
val repositoryModule = module {
    single<UserRepository> { 
        UserRepositoryImpl(
            apiService = get(),
            database = get()
        ) 
    }
    
    single<PostRepository> { 
        PostRepositoryImpl(
            apiService = get(),
            cache = get()
        ) 
    }
}

// ui/di/ViewModelModule.kt
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { (userId: String) -> 
        UserDetailViewModel(userId, get()) 
    }
}
```

### 팁 2: 환경별 설정 (개발/프로덕션)

```kotlin
val devNetworkModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL  // 모든 로그 출력
            }
        }
    }
    single { ApiService(baseUrl = "https://dev-api.example.com", get()) }
}

val prodNetworkModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.NONE  // 로그 없음
            }
        }
    }
    single { ApiService(baseUrl = "https://api.example.com", get()) }
}

fun initKoin(isDevelopment: Boolean = false) {
    startKoin {
        modules(
            listOf(
                if (isDevelopment) devNetworkModule else prodNetworkModule,
                repositoryModule,
                viewModelModule,
            )
        )
    }
}
```

### 팁 3: ViewModel with Parameters

파라미터가 있는 ViewModel을 주입하는 방법:

```kotlin
// ViewModel 정의
class UserDetailViewModel(
    private val userId: String,
    private val userRepository: UserRepository
) : ViewModel() {
    // ...
}

// DI 모듈에 등록
val viewModelModule = module {
    viewModel { (userId: String) ->
        UserDetailViewModel(userId, get())
    }
}

// Compose에서 사용
@Composable
fun UserDetailScreen(userId: String) {
    val viewModel: UserDetailViewModel = koinViewModel { 
        parametersOf(userId) 
    }
    // ... UI
}
```

### 팁 4: Qualifier 사용

같은 타입의 여러 인스턴스를 구분할 때:

```kotlin
val networkModule = module {
    single(named("main")) {
        HttpClient {
            // 메인 API 설정
        }
    }
    
    single(named("auth")) {
        HttpClient {
            // 인증 API 설정
        }
    }
}

// 사용
class UserRepository(
    @Named("main") private val mainClient: HttpClient,
    @Named("auth") private val authClient: HttpClient
)
```

### 팁 5: 테스트 설정

```kotlin
class UserRepositoryTest : KoinTest {
    
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }
    
    private val userRepository: UserRepository by inject()
    
    @Test
    fun `사용자 정보를 가져온다`() = runTest {
        // Given
        val userId = "test123"
        
        // When
        val result = userRepository.getUser(userId)
        
        // Then
        assertEquals("TestUser", result.name)
    }
}

val testModule = module {
    single<UserRepository> { FakeUserRepository() }
}

class FakeUserRepository : UserRepository {
    override suspend fun getUser(id: String): User {
        return User(id, "TestUser")
    }
}
```

### 팁 6: Compose에서 ViewModel 사용

```kotlin
@Composable
fun MainScreen() {
    // 기본 사용
    val viewModel: MainViewModel = koinViewModel()
    
    val uiState by viewModel.uiState.collectAsState()
    
    // ... UI 구현
}

@Composable
fun UserDetailScreen(userId: String) {
    // 파라미터와 함께 사용
    val viewModel: UserDetailViewModel = koinViewModel {
        parametersOf(userId)
    }
    
    // ... UI 구현
}
```

### 팁 7: Activity/Fragment에서 주입

```kotlin
class MainActivity : ComponentActivity() {
    
    // Property 주입
    private val userRepository: UserRepository by inject()
    
    // Lazy 주입
    private val apiService: ApiService by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MainScreen()
        }
    }
}
```

---

## 다음 단계

Koin 설정이 완료되었습니다! 이제 다음 작업을 진행하세요:

1. **네트워크 모듈 구현**: Ktor Client 설정 및 API 서비스 생성
2. **데이터베이스 설정**: SQLDelight�� Room 설정
3. **Repository 패턴 구현**: 데이터 소스 추상화
4. **ViewModel 생성**: UI 로직과 비즈니스 로직 분리
5. **UseCase 계층 추가** (선택): Clean Architecture 적용

---

## 참고 자료

### 공식 문서
- [Koin 공식 문서](https://insert-koin.io/)
- [Koin Compose Multiplatform](https://insert-koin.io/docs/reference/koin-compose/multiplatform)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

### 예제 프로젝트
- [KMM Sample](https://github.com/InsertKoinIO/koin-kmm)
- [Compose Multiplatform Wizard](https://kmp.jetbrains.com/)

### 커뮤니티
- [Kotlin Slack - #koin](https://kotlinlang.slack.com/)
- [Stack Overflow - koin](https://stackoverflow.com/questions/tagged/koin)

---

## 버전 히스토리

- **v1.0.0** (2026-01-02)
  - 초기 가이드 작성
  - Koin 4.1.1 기반
  - Android/iOS 플랫폼 설정
  - 문제 해결 가이드 포함
  - 한국어 버전

---

## 피드백

이 가이드에 대한 피드백이나 개선 사항이 있다면 이슈를 등록해주세요!
