import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.peto.droidmorning.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("kmpPrimitive") {
            id = "droidmorning.kmp"
            implementationClass = "com.peto.droidmorning.app.primitive.KotlinMultiPlatformPlugin"
        }
        register("kmpAndroid") {
            id = "droidmorning.kmp.android"
            implementationClass = "com.peto.droidmorning.app.primitive.KotlinMultiPlatformAndroidPlugin"
        }
        register("kmpIos") {
            id = "droidmorning.kmp.ios"
            implementationClass = "com.peto.droidmorning.app.primitive.KotlinMultiPlatformiOSPlugin"
        }
        register("androidLibrary") {
            id = "droidmorning.android.library"
            implementationClass = "com.peto.droidmorning.app.AndroidLibraryConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "droidmorning.kotlin.multiplatform"
            implementationClass = "com.peto.droidmorning.app.KotlinMultiplatformConventionPlugin"
        }
        register("composeMultiplatform") {
            id = "droidmorning.compose.multiplatform"
            implementationClass = "com.peto.droidmorning.app.ComposeMultiplatformConventionPlugin"
        }
        register("droidmorningFeature") {
            id = "droidmorning.feature"
            implementationClass = "com.peto.droidmorning.app.DroidMorningFeaturePlugin"
        }
    }
}
