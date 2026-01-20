package com.peto.droidmorning

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply<KotlinMultiPlatformPlugin>()
        apply<KotlinMultiPlatformAndroidPlugin>()
        apply<KotlinMultiPlatformiOSPlugin>()
    }
}
