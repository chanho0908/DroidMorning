package com.peto.droidmorning

import com.peto.droidmorning.extentions.bundle
import com.peto.droidmorning.extentions.composeMultiplatformDependencies
import com.peto.droidmorning.extentions.library
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * DroidMorning Feature 모듈 통합 플러그인
 * Feature 모듈에 필요한 기본 설정을 제공합니다.
 * 프로젝트 의존성(domain, designsystem 등)은 각 모듈에서 직접 추가해야 합니다.
 */
class DroidMorningFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugin("android-library").pluginId)
            apply(libs.plugin("kotlin-multiplatform").pluginId)
            apply(libs.plugin("compose-multiplatform").pluginId)
            apply(libs.plugin("kotlin-compose").pluginId)
            apply(libs.plugin("kotlin-serialization").pluginId)
        }

        apply<KotlinMultiPlatformPlugin>()
        apply<KotlinMultiPlatformAndroidPlugin>()
        apply<KotlinMultiPlatformiOSPlugin>()

        composeMultiplatformDependencies()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain {
                    dependencies {
                        implementation(libs.library("androidx-navigation-compose"))
                        implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                        implementation(libs.library("androidx-lifecycle-runtime-compose"))
                        
                        implementation(libs.bundle("koin"))
                        
                        implementation(libs.library("kotlinx-coroutines-core"))
                        implementation(libs.library("kotlinx-serialization-json"))
                        implementation(libs.library("kotlinx-collections-immutable"))
                        implementation(libs.library("napier"))
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
