# Build Logic

DroidMorning 프로젝트의 Gradle Convention Plugin 모듈입니다.
각 모듈의 빌드 설정을 중앙화하고 일관성있게 관리합니다.

## 📦 제공하는 Convention Plugins

### 1. `droidmorning.kotlin.multiplatform`
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
- 공통 테스트 의존성

### 2. `droidmorning.android.library`
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

### 3. `droidmorning.compose.multiplatform`
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
- 기본 Compose 의존성 (runtime, foundation, material3, ui 등)

## 🚀 사용 예시

### Feature 모듈 생성 시
```kotlin
// feature/home/build.gradle.kts
plugins {
    id("droidmorning.kotlin.multiplatform")
    id("droidmorning.android.library")
    id("droidmorning.compose.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.designsystem)
            // 추가 의존성...
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
│       ├── AndroidLibraryConventionPlugin.kt
│       ├── KotlinMultiplatformConventionPlugin.kt
│       └── ComposeMultiplatformConventionPlugin.kt
└── settings.gradle.kts
```

## 🔧 수정 방법

Convention Plugin을 수정한 후에는 Gradle sync를 실행하여 변경사항을 적용해야 합니다:

```bash
./gradlew --stop
./gradlew tasks
```
