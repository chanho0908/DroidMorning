This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Dependency Injection - Koin

This project uses [Koin](https://insert-koin.io/) for dependency injection across all platforms.

📖 **[Koin Setup Guide](./docs/KOIN_SETUP_GUIDE.md)** - Complete guide for setting up Koin in KMP projects

**Quick Overview:**
- **Version**: Koin 4.1.1
- **Modules**: Network, Repository, ViewModel
- **Platforms**: Android (with androidContext) & iOS (with doInitKoin)

**Key Files:**
- [KoinInitializer.kt](./composeApp/src/commonMain/kotlin/com/peto/droidmorning/KoinInitializer.kt) - Common initialization
- [DroidMorningApp.kt](./composeApp/src/androidMain/kotlin/com/peto/droidmorning/DroidMorningApp.kt) - Android setup
- [iOSApp.swift](./iosApp/iosApp/iOSApp.swift) - iOS setup

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Code Style - ktlint

This project uses [ktlint](https://github.com/pinterest/ktlint) to enforce consistent Kotlin code style.

#### Check code style
```shell
./gradlew ktlintCheck
```

#### Auto-format code
```shell
./gradlew ktlintFormat
```

The ktlint configuration is defined in [.editorconfig](./.editorconfig).

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

# DroidMorning
