package com.peto.droidmorning.app.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * iOS 타겟 설정 플러그인
 */
class KotlinMultiPlatformiOSPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            iosArm64()
            iosX64()
            iosSimulatorArm64()

            targets.withType<KotlinNativeTarget> {
                compilations["main"].compilerOptions.configure {
                    freeCompilerArgs.add("-Xexport-kdoc")
                }
                binaries.framework {
                    baseName = project.name
                    isStatic = true
                }
            }
        }
    }
}
