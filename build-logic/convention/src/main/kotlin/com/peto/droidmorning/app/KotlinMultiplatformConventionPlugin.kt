package com.peto.droidmorning.app

import com.peto.droidmorning.app.primitive.KotlinMultiPlatformAndroidPlugin
import com.peto.droidmorning.app.primitive.KotlinMultiPlatformPlugin
import com.peto.droidmorning.app.primitive.KotlinMultiPlatformiOSPlugin
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
            apply(libs.findPlugin("kotlin-multiplatform").get().get().pluginId)
        }

        apply<KotlinMultiPlatformPlugin>()
        apply<KotlinMultiPlatformAndroidPlugin>()
        apply<KotlinMultiPlatformiOSPlugin>()
    }
}
