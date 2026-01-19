# Build Logic

DroidMorning 프로젝트의 Gradle Convention Plugin 모듈입니다.
각 모듈의 빌드 설정을 중앙화하고 일관성있게 관리합니다.

## 📦 제공하는 Convention Plugins

### 🎯 High-Level Plugins (권장)

#### 1. `droidmorning.feature` ⭐
Feature 모듈을 위한 통합 플러그인입니다. 한 번에 모든 설정을 적용합니다.

**적용 방법:**
```kotlin
plugins {
    id("droidmorning.feature")
}
```

**포함 설정:**
- Kotlin Multiplatform (Android JVM 11 + iOS)
- Compose Multiplatform (UI, Material3, Navigation)
- Kotlin Serialization (JSON 직렬화)
- 기본 의존성:
  - **프로젝트**: domain, designsystem
  - **Navigation**: androidx-navigation-compose
  - **Lifecycle**: androidx-lifecycle-viewmodel-compose, androidx-lifecycle-runtime-compose
  - **DI**: koin bundle (core, compose, compose-viewmodel)
  - **Kotlinx**: coroutines-core, serialization-json, datetime
  - **테스트**: kotlin-test, koin-test, kotlinx-coroutines-test

**사용 예시:**
```kotlin
// feature/home/build.gradle.kts
plugins {
    id("droidmorning.feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // feature 플러그인이 자동으로 추가하는 의존성:
            // ✅ domain, designsystem 프로젝트
            // ✅ navigation, lifecycle, koin
            // ✅ coroutines, serialization, datetime
            // ✅ Compose Multiplatform (UI, Material3 등)
            
            // 추가 의존성만 여기에 작성
            implementation(libs.ktor.client.core)
        }
    }
}
```

#### 2. `droidmorning.kotlin.multiplatform`
Kotlin Multiplatform 공통 설정을 제공합니다.

**적용 방법:**
```kotlin
plugins {
    id("droidmorning.kotlin.multiplatform")
}
```

**포함 설정:**
- Android target (JVM 17)
- iOS targets (iosX64, iosArm64, iosSimulatorArm64)
- 기본 소스셋 계층 구조

#### 3. `droidmorning.compose.multiplatform`
Compose Multiplatform 공통 설정을 제공합니다.

**적용 방법:**
```kotlin
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.compose.multiplatform")
}
```

**포함 설정:**
- Compose Multiplatform 플러그인
- Compose Compiler 플러그인
- 기본 Compose 의존성 (runtime, foundation, material3, ui, resources 등)

#### 4. `droidmorning.android.library`
Android Library 공통 설정을 제공합니다.

**적용 방법:**
```kotlin
plugins {
    id("droidmorning.android.library")
}
```

**포함 설정:**
- compileSdk: 35
- minSdk: 26
- Java 17 호환성
- BuildConfig 활성화

### 🔧 Primitive Plugins (고급)

세밀한 제어가 필요한 경우 사용하는 저수준 플러그인들입니다.

- `droidmorning.kmp` - 기본 Kotlin Multiplatform 설정
- `droidmorning.kmp.android` - Android 타겟 설정
- `droidmorning.kmp.ios` - iOS 타겟 설정

## 🚀 사용 예시

### Feature 모듈 생성 (권장)
```kotlin
// feature/home/build.gradle.kts
plugins {
    id("droidmorning.feature")
}

// 기본 설정이 모두 포함되어 있으므로 추가 의존성만 작성
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
    }
}
```

### Core 모듈 생성
```kotlin
// core/network/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.android.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
```

### UI 모듈 생성
```kotlin
// core/ui/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.android.library")
    id("droidmorning.compose.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.designsystem)
        }
    }
}
```

## 📁 디렉토리 구조

```
build-logic/
├── convention/
│   ├── build.gradle.kts
│   └── src/main/kotlin/com/peto/droidmorning/app/
│       ├── Extensions.kt                              # 유틸리티 확장 함수
│       ├── AndroidLibraryConventionPlugin.kt          # Android Library 플러그인
│       ├── KotlinMultiplatformConventionPlugin.kt     # KMP 컨벤션 플러그인
│       ├── ComposeMultiplatformConventionPlugin.kt    # Compose 컨벤션 플러그인
│       ├── DroidMorningFeaturePlugin.kt               # Feature 통합 플러그인
│       └── primitive/
│           ├── ComposeExtensions.kt                   # Compose 확장 함수
│           ├── KotlinMultiPlatformPlugin.kt           # 기본 KMP 플러그인
│           ├── KotlinMultiPlatformAndroidPlugin.kt    # Android 타겟 플러그인
│           └── KotlinMultiPlatformiOSPlugin.kt        # iOS 타겟 플러그인
└── settings.gradle.kts
```

## 🎨 아키텍처

```
High-Level Plugins
├─ droidmorning.feature (모든 것 포함)
│  └─ 내부적으로 primitive 플러그인들 조합
│
├─ droidmorning.kotlin.multiplatform
│  ├─ droidmorning.kmp
│  ├─ droidmorning.kmp.android
│  └─ droidmorning.kmp.ios
│
└─ droidmorning.compose.multiplatform
   └─ Compose 의존성 자동 설정
```

## 🔧 수정 방법

Convention Plugin을 수정한 후에는 Gradle sync를 실행하여 변경사항을 적용해야 합니다:

```bash
./gradlew --stop
./gradlew tasks
```

## 💡 팁

1. **Feature 모듈을 만들 때는 `droidmorning.feature`만 사용하세요** - 가장 간단하고 빠릅니다.
2. **Core 모듈은 필요한 플러그인만 선택적으로 적용하세요** - 불필요한 의존성을 피할 수 있습니다.
3. **Primitive 플러그인은 특별한 경우가 아니면 직접 사용하지 마세요** - High-level 플러그인이 더 편리합니다.
