plugins {
    alias(libs.plugins.droidmorning.kotlin.multiplatform)
    alias(libs.plugins.droidmorning.android.library)
    alias(libs.plugins.droidmorning.koin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.datastore)
        }
        iosMain.dependencies {
            implementation(libs.okio)
        }
    }
}

android {
    namespace = "com.peto.droidmorning.core.datastore"
}
