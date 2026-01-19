import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.peto.droidmorning.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
}

gradlePlugin {
    plugins {
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
    }
}
