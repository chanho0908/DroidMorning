package com.peto.droidmorning.app

import com.peto.droidmorning.app.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Compose Multiplatform 컨벤션 플러그인
 * 공통 Compose Multiplatform 설정을 중앙화합니다.
 */
class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("compose-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
        }

        composeMultiplatformDependencies()
    }
}
