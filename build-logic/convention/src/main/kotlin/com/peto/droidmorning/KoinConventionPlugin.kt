package com.peto.droidmorning

import com.peto.droidmorning.extentions.koinDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        koinDependencies()
    }
}
