# Build Logic

DroidMorning 프로젝트의 Gradle Convention Plugin 모듈입니다.
각 모듈의 빌드 설정을 중앙화하고 일관성있게 관리합니다.

## 📦 제공하는 Convention Plugins

### 🎯 High-Level Plugins (권장)

#### 1. `droidmorning.feature` ⭐
Feature 모듈을 위한 통합 플러그인입니다. UI 기능 모듈에 필요한 기본 설정을 제공합니다.

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
  - **Navigation**: androidx-navigation-compose
  - **Lifecycle**: androidx-lifecycle-viewmodel-compose, androidx-lifecycle-runtime-compose
  - **DI**: koin bundle (core, compose, compose-viewmodel)
  - **Kotlinx**: coroutines-core, serialization-json, collections-immutable
  - **Logging**: napier
  - **테스트**: kotlin-test, koin-test, kotlinx-coroutines-test

**사용 예시:**
```kotlin
// feature/exam/detail/build.gradle.kts
plugins {
    id("droidmorning.feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // 필요한 core 모듈을 명시적으로 추가
            implementation(projects.core.domain)
            implementation(projects.core.designSystem)
            implementation(projects.core.ui)
            
            // 추가 의존성
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

### 권장 모듈 구조
```
📁 build-logic              // Convention Plugin
📁 app                      // Application 진입점

📁 core
 ├─ :core:common            // 공용 유틸
 ├─ :core:ui                // 공용 UI
 ├─ :core:navigation        // Navigation
 ├─ :core:design-system     // 디자인 시스템
 ├─ :core:network           // 네트워크
 ├─ :core:domain            // Domain
 └─ :core:data              // Data

📁 feature
 ├─ :feature:exam:complete  // Feature
 └─ :feature:exam:detail    // Feature
```

### Feature 모듈 생성
```kotlin
// feature/exam/detail/build.gradle.kts
plugins {
    id("droidmorning.feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // 필요한 core 모듈 추가
            implementation(projects.core.domain)
            implementation(projects.core.designSystem)
            implementation(projects.core.ui)
            implementation(projects.core.navigation)
        }
    }
}
```

### Core 모듈 생성

각 Core 모듈은 기본 플러그인을 조합하여 사용합니다.

#### Domain 모듈 (순수 모델 + Repository Interface)
```kotlin
// core/domain/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
}

// 순수 도메인 - 추가 의존성 불필요
```

#### Data 모듈 (DataSource + Repository 구현)
```kotlin
// core/data/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.network)
            
            // DI & Kotlinx
            implementation(libs.bundles.koin)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
```

#### Network 모듈 (Ktor + Supabase)
```kotlin
// core/network/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            // Network
            implementation(libs.bundles.ktor.common)
            implementation(platform(libs.supabase.bom))
            implementation(libs.bundles.supabase)
            
            // DI & Kotlinx
            implementation(libs.bundles.koin)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            
            // Logging
            implementation(libs.napier)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
```

#### UI 모듈 (공용 Composable)
```kotlin
// core/ui/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.compose.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.designSystem)
        }
    }
}
```

#### Common 모듈 (유틸리티)
```kotlin
// core/common/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.napier)
        }
    }
}
```

#### Navigation 모듈
```kotlin
// core/navigation/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.compose.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
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
│       ├── extentions/
│       │   ├── ProjectExtensions.kt                   # Project 확장 함수
│       │   └── VersionCatalogExtensions.kt            # VersionCatalog 확장 함수
│       │
│       ├── primitive/                                 # 저수준 플러그인
│       │   ├── KotlinMultiPlatformPlugin.kt           # 기본 KMP 플러그인
│       │   ├── KotlinMultiPlatformAndroidPlugin.kt    # Android 타겟 플러그인
│       │   └── KotlinMultiPlatformiOSPlugin.kt        # iOS 타겟 플러그인
│       │
│       ├── AndroidLibraryConventionPlugin.kt          # Android Library 플러그인
│       ├── KotlinMultiplatformConventionPlugin.kt     # KMP 컨벤션 플러그인
│       ├── ComposeMultiplatformConventionPlugin.kt    # Compose 컨벤션 플러그인
│       └── DroidMorningFeaturePlugin.kt               # Feature 통합 플러그인
└── settings.gradle.kts
```

## 🎨 아키텍처

```
Convention Plugins 계층 구조

High-Level Plugins (Feature용)
└─ droidmorning.feature
   ├─ KMP (Android + iOS)
   ├─ Compose Multiplatform
   ├─ Serialization
   └─ 기본 의존성 (Navigation, Lifecycle, Koin 등)

Base Plugins (Core 모듈용 - 조합해서 사용)
├─ droidmorning.kotlin.multiplatform
│  ├─ droidmorning.kmp
│  ├─ droidmorning.kmp.android
│  └─ droidmorning.kmp.ios
│
├─ droidmorning.compose.multiplatform
│  └─ Compose 의존성 자동 설정
│
└─ droidmorning.android.library
   └─ Android Library 기본 설정

Primitive Plugins (고급 사용자용)
├─ droidmorning.kmp
├─ droidmorning.kmp.android
└─ droidmorning.kmp.ios
```

## 🔧 수정 방법

Convention Plugin을 수정한 후에는 Gradle sync를 실행하여 변경사항을 적용해야 합니다:

```bash
./gradlew --stop
./gradlew tasks
```

## 💡 팁

1. **Feature 모듈은 `droidmorning.feature` 플러그인을 사용하세요**
   - UI 기능 모듈에 필요한 모든 기본 설정이 포함되어 있습니다
   - 필요한 core 모듈만 명시적으로 추가하세요

2. **Core 모듈은 기본 플러그인을 조합하여 사용하세요**
   - 각 모듈의 특성에 맞게 플러그인을 선택하고 조합합니다
   - 예: Domain → `kotlin.multiplatform`, Network → `kotlin.multiplatform` + `serialization`
   - 필요한 의존성은 각 모듈에서 명시적으로 추가합니다

3. **의존성은 명시적으로 관리하세요**
   - Convention Plugin은 공통 설정만 제공합니다
   - 각 모듈에서 필요한 의존성을 직접 추가하면 의존 관계가 명확해집니다
   - 불필요한 의존성을 피할 수 있어 빌드 시간이 단축됩니다

4. **Primitive 플러그인은 특별한 경우가 아니면 직접 사용하지 마세요**
   - High-level 플러그인이 더 편리하고 일관성있는 설정을 제공합니다
