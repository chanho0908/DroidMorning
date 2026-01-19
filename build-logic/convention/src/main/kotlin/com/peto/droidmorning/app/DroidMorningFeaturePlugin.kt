package com.peto.droidmorning.app

import com.peto.droidmorning.app.primitive.KotlinMultiPlatformAndroidPlugin
import com.peto.droidmorning.app.primitive.KotlinMultiPlatformPlugin
import com.peto.droidmorning.app.primitive.KotlinMultiPlatformiOSPlugin
import com.peto.droidmorning.app.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * DroidMorning Feature 모듈 통합 플러그인
 * Feature 모듈에 필요한 모든 설정을 한번에 적용합니다.
 */
class DroidMorningFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("android-library").get().get().pluginId)
            apply(libs.findPlugin("kotlin-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("compose-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
            apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
        }

        apply<KotlinMultiPlatformPlugin>()
        apply<KotlinMultiPlatformAndroidPlugin>()
        apply<KotlinMultiPlatformiOSPlugin>()

        composeMultiplatformDependencies()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain {
                    dependencies {
                        implementation(project(":domain"))
                        implementation(project(":designsystem"))
                        
                        // Navigation & Lifecycle
                        implementation(libs.library("androidx-navigation-compose"))
                        implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                        implementation(libs.library("androidx-lifecycle-runtime-compose"))
                        
                        // Dependency Injection
                        implementation(libs.bundle("koin"))
                        
                        // Kotlinx
                        implementation(libs.library("kotlinx-coroutines-core"))
                        implementation(libs.library("kotlinx-serialization-json"))
                        implementation(libs.library("kotlinx-datetime"))
                    }
                }
                commonTest {
                    dependencies {
                        implementation(libs.library("kotlin-test"))
                        implementation(libs.library("koin-test"))
                        implementation(libs.library("kotlinx-coroutines-test"))
                    }
                }
            }
        }
    }
}
