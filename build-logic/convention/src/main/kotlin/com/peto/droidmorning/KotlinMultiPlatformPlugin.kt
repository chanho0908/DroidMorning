package com.peto.droidmorning

import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

class KotlinMultiPlatformPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugin("kotlin-multiplatform").pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }

            applyDefaultHierarchyTemplate()
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        tasks.withType<KotlinNativeLink>().configureEach {
            notCompatibleWithConfigurationCache("Configuration cache not supported for a system property read at configuration time")
        }
    }
}
