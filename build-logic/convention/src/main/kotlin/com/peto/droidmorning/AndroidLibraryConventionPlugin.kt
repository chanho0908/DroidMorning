package com.peto.droidmorning

import com.android.build.gradle.LibraryExtension
import com.peto.droidmorning.extentions.javaVersion
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import com.peto.droidmorning.extentions.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("android-library").pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = libs.version("compileSdk").toInt()

                defaultConfig {
                    minSdk = libs.version("minSdk").toInt()
                }

                compileOptions {
                    sourceCompatibility = libs.javaVersion("java")
                    targetCompatibility = libs.javaVersion("java")
                }

                buildFeatures {
                    buildConfig = true
                }
            }
        }
    }
}
