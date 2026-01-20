import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.peto.droidmorning.buildlogic"

val javaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("kmpPrimitive") {
            id = "droidmorning.kmp"
            implementationClass = "com.peto.droidmorning.KotlinMultiPlatformPlugin"
        }
        register("kmpAndroid") {
            id = "droidmorning.kmp.android"
            implementationClass = "com.peto.droidmorning.KotlinMultiPlatformAndroidPlugin"
        }
        register("kmpIos") {
            id = "droidmorning.kmp.ios"
            implementationClass = "com.peto.droidmorning.KotlinMultiPlatformiOSPlugin"
        }
        register("androidLibrary") {
            id = "droidmorning.android.library"
            implementationClass = "com.peto.droidmorning.AndroidLibraryConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "droidmorning.kotlin.multiplatform"
            implementationClass = "com.peto.droidmorning.KotlinMultiplatformConventionPlugin"
        }
        register("composeMultiplatform") {
            id = "droidmorning.compose.multiplatform"
            implementationClass = "com.peto.droidmorning.ComposeMultiplatformConventionPlugin"
        }
        register("droidmorningFeature") {
            id = "droidmorning.feature"
            implementationClass = "com.peto.droidmorning.DroidMorningFeaturePlugin"
        }
    }
}
