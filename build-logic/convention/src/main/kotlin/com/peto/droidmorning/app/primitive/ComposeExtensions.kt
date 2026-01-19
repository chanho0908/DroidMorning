package com.peto.droidmorning.app.primitive

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}

val Project.compose: ComposeExtension
    get() = extensions.getByType()

fun ComposeExtension.resources(block: ResourcesExtension.() -> Unit) {
    extensions.configure<ResourcesExtension>(block)
}

internal fun Project.composeMultiplatformDependencies() {
    val composeDependencies = extensions.getByType<ComposeExtension>().dependencies
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            commonMain {
                dependencies {
                    implementation(composeDependencies.runtime)
                    implementation(composeDependencies.foundation)
                    implementation(composeDependencies.material3)
                    implementation(composeDependencies.ui)
                    implementation(composeDependencies.components.resources)
                    implementation(composeDependencies.components.uiToolingPreview)
                }
            }
        }
    }

    dependencies {
        "debugImplementation"("org.jetbrains.compose.ui:ui-tooling:1.10.0")
    }
}
