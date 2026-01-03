# CocoaPods 설정 가이드

## 📋 목차
1. [CocoaPods란?](#1-cocoapods란)
2. [CocoaPods 설치](#2-cocoapods-설치)
3. [프로젝트 설정](#3-프로젝트-설정)
4. [Xcode 설정](#4-xcode-설정)
5. [문제 해결](#5-문제-해결)

---

## 1. CocoaPods란?

### 1.1 개요
CocoaPods는 Swift와 Objective-C Cocoa 프로젝트의 의존성 관리자입니다. Kotlin Multiplatform에서 iOS 앱이 공유 Kotlin 코드를 사용하기 위해 필요합니다.

### 1.2 왜 필요한가?
- Kotlin Multiplatform의 공유 모듈을 iOS 프로젝트에 연결
- iOS 네이티브 라이브러리 의존성 관리
- 빌드 프로세스 자동화

---

## 2. CocoaPods 설치

### 2.1 필수 요구사항
- macOS
- Ruby (macOS에 기본 포함)
- Xcode Command Line Tools

### 2.2 CocoaPods 설치
```bash
# Homebrew를 사용한 설치 (권장)
brew install cocoapods

# 또는 gem을 사용한 설치
sudo gem install cocoapods
```

### 2.3 설치 확인
```bash
pod --version
```

성공적으로 설치되면 버전 번호가 표시됩니다 (예: `1.15.2`).

---

## 3. 프로젝트 설정

### 3.1 Gradle에 CocoaPods 플러그인 추가

`composeApp/build.gradle.kts` 파일을 수정합니다:

```kotlin
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    kotlin("native.cocoapods") // 이 줄 추가
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    // CocoaPods 설정 추가
    cocoapods {
        version = "1.0"
        summary = "DroidMorning Shared Module"
        homepage = "https://github.com/yourusername/DroidMorning"
        
        ios.deploymentTarget = "14.0"
        
        // Framework 설정
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        
        // iOS 의존성이 필요한 경우 여기에 추가
        // pod("AFNetworking") { version = "~> 4.0.1" }
    }

    sourceSets {
        // ... 기존 설정 유지
    }
}
```

### 3.2 Podfile 생성

프로젝트를 동기화하면 Gradle이 자동으로 `iosApp` 폴더에 Podfile을 생성합니다:

```bash
# Android Studio에서 Gradle 동기화
# 또는 터미널에서:
./gradlew :composeApp:podInstall
```

이 명령은 다음을 수행합니다:
- `iosApp/Podfile` 생성
- `iosApp/Pods` 폴더 생성
- `iosApp/ComposeApp.podspec` 생성

### 3.3 생성된 Podfile 확인

`iosApp/Podfile` 파일이 다음과 같이 생성됩니다:

```ruby
target 'iosApp' do
  use_frameworks!
  platform :ios, '14.0'
  
  pod 'ComposeApp', :path => '../composeApp'
end
```

### 3.4 Pod 설치

```bash
cd iosApp
pod install
```

성공 메시지를 확인합니다:
```
Pod installation complete! There is 1 dependency from the Podfile and 1 total pod installed.
```

---

## 4. Xcode 설정

### 4.1 Workspace 열기

**중요**: Pod 설치 후에는 반드시 `.xcworkspace` 파일을 사용해야 합니다!

```bash
# .xcodeproj가 아닌 .xcworkspace 열기
open iosApp/iosApp.xcworkspace
```

또는 Xcode에서:
- File → Open → `iosApp/iosApp.xcworkspace` 선택

### 4.2 프로젝트 구조 확인

Xcode 왼쪽 패널에서 다음이 보여야 합니다:
```
iosApp.xcworkspace
├── iosApp (앱 프로젝트)
└── Pods (의존성)
    └── ComposeApp
```

### 4.3 빌드 설정 확인

1. Xcode에서 프로젝트 선택
2. **General** 탭에서:
   - **Deployment Target**: 14.0 이상
   - **Frameworks, Libraries, and Embedded Content**에 `ComposeApp.framework`가 있는지 확인

3. **Build Settings** 탭에서:
   - **Framework Search Paths**에 `$(SRCROOT)/Pods` 경로 확인

### 4.4 빌드 및 실행

1. 시뮬레이터 또는 실제 기기 선택
2. **Product** → **Build** (⌘B)
3. **Product** → **Run** (⌘R)

---

## 5. 문제 해결

### 5.1 "framework not found ComposeApp" 오류

**원인**: Workspace 대신 Project 파일을 열었거나 pod install이 실행되지 않음

**해결 방법**:
```bash
cd iosApp
pod install
# 그 후 .xcworkspace 파일 열기
open iosApp.xcworkspace
```

### 5.2 "No such module 'ComposeApp'" 오류

**원인**: Framework가 제대로 빌드되지 않음

**해결 방법**:
```bash
# 1. Gradle로 Framework 빌드
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode

# 2. Xcode에서 Clean Build Folder
# Xcode → Product → Clean Build Folder (⇧⌘K)

# 3. 다시 빌드
```

### 5.3 "Unable to find a specification for ComposeApp" 오류

**원인**: podspec 파일이 생성되지 않음

**해결 방법**:
```bash
# 1. podspec 생성
./gradlew :composeApp:podspec

# 2. pod install 다시 실행
cd iosApp
pod install
```

### 5.4 Ruby 버전 문제

**에러**: `Your Ruby version is X.X.X, but your Gemfile specified Y.Y.Y`

**해결 방법**:
```bash
# rbenv 사용 (권장)
brew install rbenv ruby-build
rbenv install 3.3.0
rbenv global 3.3.0

# 또는 Bundler 사용
bundle install
bundle exec pod install
```

### 5.5 M1/M2 Mac에서 아키텍처 오류

**에러**: `building for iOS Simulator, but linking in object file built for iOS`

**해결 방법 1**: Podfile에 추가
```ruby
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['EXCLUDED_ARCHS[sdk=iphonesimulator*]'] = 'arm64'
    end
  end
end
```

**해결 방법 2**: Xcode 빌드 설정
1. **Build Settings** → **Excluded Architectures**
2. **Debug** → **Any iOS Simulator SDK**: `arm64` 추가

### 5.6 Gradle Sync 실패

**원인**: CocoaPods 플러그인 버전 문제

**해결 방법**: `gradle/libs.versions.toml` 확인
```toml
[versions]
kotlin = "2.3.0"  # 최신 버전 사용

[plugins]
kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
```

### 5.7 Pod 캐시 문제

**증상**: 변경사항이 반영되지 않음

**해결 방법**:
```bash
cd iosApp
pod deintegrate
pod cache clean --all
pod install
```

---

## 6. 유용한 명령어

### Gradle 명령어
```bash
# Framework 빌드
./gradlew :composeApp:linkDebugFrameworkIosArm64

# Podspec 생성
./gradlew :composeApp:podspec

# Pod 설치 (Gradle에서)
./gradlew :composeApp:podInstall

# Pod 업데이트
./gradlew :composeApp:podPublishXCFramework
```

### CocoaPods 명령어
```bash
# Pod 설치
pod install

# Pod 업데이트
pod update

# 특정 Pod 업데이트
pod update ComposeApp

# Pod 캐시 정리
pod cache clean --all

# Pod 제거 후 재설치
pod deintegrate
pod install

# Podfile.lock 무시하고 설치
pod install --repo-update
```

### Xcode 명령어
```bash
# Workspace 열기
open iosApp/iosApp.xcworkspace

# 터미널에서 빌드
xcodebuild -workspace iosApp/iosApp.xcworkspace \
  -scheme iosApp \
  -configuration Debug \
  -destination 'platform=iOS Simulator,name=iPhone 15'

# DerivedData 정리
rm -rf ~/Library/Developer/Xcode/DerivedData
```

---

## 7. 개발 워크플로우

### 7.1 일반적인 개발 사이클

1. **Kotlin 코드 변경**
   ```bash
   # Android Studio에서 Kotlin 코드 수정
   ```

2. **Framework 재빌드**
   ```bash
   ./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
   ```

3. **Xcode에서 빌드 및 실행**
   ```bash
   # Xcode에서 ⌘R
   ```

### 7.2 새로운 iOS 의존성 추가

1. **build.gradle.kts에 pod 추가**
   ```kotlin
   cocoapods {
       // 예: Alamofire 추가
       pod("Alamofire") {
           version = "~> 5.8.0"
       }
   }
   ```

2. **Gradle 동기화**
   ```bash
   ./gradlew :composeApp:podInstall
   ```

3. **Xcode에서 확인**
   - Pods 프로젝트에서 새로운 라이브러리 확인

### 7.3 CI/CD 설정 팁

```yaml
# .github/workflows/ios.yml 예제
name: iOS Build

on: [push, pull_request]

jobs:
  build:
    runs-on: macos-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Setup CocoaPods
      run: |
        gem install cocoapods
        pod --version
    
    - name: Build Framework
      run: ./gradlew :composeApp:podInstall
    
    - name: Install Pods
      run: |
        cd iosApp
        pod install
    
    - name: Build iOS App
      run: |
        xcodebuild -workspace iosApp/iosApp.xcworkspace \
          -scheme iosApp \
          -configuration Debug \
          -destination 'platform=iOS Simulator,name=iPhone 15' \
          build
```

---

## 8. 추가 리소스

### 공식 문서
- [Kotlin Multiplatform - CocoaPods](https://kotlinlang.org/docs/native-cocoapods.html)
- [CocoaPods 공식 가이드](https://guides.cocoapods.org/)
- [Apple Developer Documentation](https://developer.apple.com/documentation/)

### 커뮤니티
- [Kotlin Slack - #multiplatform](https://kotlinlang.slack.com/)
- [Stack Overflow - kotlin-multiplatform](https://stackoverflow.com/questions/tagged/kotlin-multiplatform)

### 유용한 도구
- [CocoaPods App](https://cocoapods.org/app) - GUI 도구
- [Podfile 문법 가이드](https://guides.cocoapods.org/syntax/podfile.html)

---

## 9. 체크리스트

프로젝트 설정이 완료되었는지 확인하세요:

- [ ] CocoaPods 설치 완료 (`pod --version` 확인)
- [ ] `build.gradle.kts`에 `kotlin("native.cocoapods")` 플러그인 추가
- [ ] `cocoapods {}` 블록 설정 완료
- [ ] `./gradlew :composeApp:podInstall` 실행 성공
- [ ] `iosApp/Podfile` 파일 생성 확인
- [ ] `cd iosApp && pod install` 실행 성공
- [ ] `iosApp.xcworkspace` 파일로 Xcode 열기
- [ ] Xcode에서 빌드 성공 (⌘B)
- [ ] 시뮬레이터에서 앱 실행 성공 (⌘R)

---

## 📝 참고사항

- **항상 `.xcworkspace` 사용**: `.xcodeproj`가 아닌 `.xcworkspace`를 열어야 합니다
- **Clean Build 자주**: 문제 발생 시 Xcode → Product → Clean Build Folder (⇧⌘K)
- **Pod 버전 관리**: `Podfile.lock`을 Git에 커밋하여 팀원과 동일한 버전 사용
- **M1/M2 Mac**: 아키텍처 관련 이슈가 발생할 수 있으니 위의 문제 해결 섹션 참조

---

**작성일**: 2026-01-03  
**Kotlin 버전**: 2.3.0  
**최소 iOS 버전**: 14.0
