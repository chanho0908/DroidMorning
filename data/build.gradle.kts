plugins {
    alias(libs.plugins.droidmorning.kotlin.multiplatform)
    alias(libs.plugins.droidmorning.android.library)
    alias(libs.plugins.droidmorning.koin)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(project(":core:network"))
            implementation(project(":core:datastore"))

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
        }
    }
}

android {
    namespace = "com.peto.droidmorning.data"
}
