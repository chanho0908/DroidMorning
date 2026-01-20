package com.peto.droidmorning

import com.android.build.gradle.LibraryExtension
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.version
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = libs.version("compileSdk").toInt()

                defaultConfig {
                    minSdk = libs.version("minSdk").toInt()
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                buildFeatures {
                    buildConfig = true
                }
            }
        }
    }
}
