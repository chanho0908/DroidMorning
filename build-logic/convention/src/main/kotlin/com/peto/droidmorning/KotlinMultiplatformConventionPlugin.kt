package com.peto.droidmorning

import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Kotlin Multiplatform 컨벤션 플러그인
 * 공통 KMP 설정을 중앙화합니다.
 */
class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugin("kotlin-multiplatform").pluginId)
        }

        apply<KotlinMultiPlatformPlugin>()
        apply<KotlinMultiPlatformAndroidPlugin>()
        apply<KotlinMultiPlatformiOSPlugin>()
    }
}
