package com.peto.droidmorning

import com.peto.droidmorning.extentions.composeMultiplatformDependencies
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Compose Multiplatform 컨벤션 플러그인
 * 공통 Compose Multiplatform 설정을 중앙화합니다.
 */
class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugin("compose-multiplatform").pluginId)
            apply(libs.plugin("kotlin-compose").pluginId)
        }

        composeMultiplatformDependencies()
    }
}
