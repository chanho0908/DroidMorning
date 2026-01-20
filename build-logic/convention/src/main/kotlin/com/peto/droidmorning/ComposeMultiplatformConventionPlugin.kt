package com.peto.droidmorning

import com.peto.droidmorning.extentions.composeMultiplatformDependencies
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugin("compose-multiplatform").pluginId)
            apply(libs.plugin("kotlin-compose").pluginId)
        }

        composeMultiplatformDependencies()
    }
}
