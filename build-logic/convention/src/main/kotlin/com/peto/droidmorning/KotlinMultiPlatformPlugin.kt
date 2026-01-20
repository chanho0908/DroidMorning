package com.peto.droidmorning

import com.peto.droidmorning.extentions.jvmTarget
import com.peto.droidmorning.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

class KotlinMultiPlatformPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }

            applyDefaultHierarchyTemplate()
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(libs.jvmTarget("java"))
            }
        }

        tasks.withType<KotlinNativeLink>().configureEach {
            notCompatibleWithConfigurationCache("Configuration cache not supported for a system property read at configuration time")
        }
    }
}
