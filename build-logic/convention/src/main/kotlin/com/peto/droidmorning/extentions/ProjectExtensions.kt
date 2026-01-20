package com.peto.droidmorning.extentions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.androidExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.findByType(LibraryExtension::class.java)
        ?: extensions.findByType(ApplicationExtension::class.java)
        ?: error("Could not find Library or Application extension from this project")

internal fun Project.composeMultiplatformDependencies() {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            commonMain {
                dependencies {
                    implementation(libs.bundle("compose-multiplatform"))
                }
            }
        }
    }

    configurations.findByName("debugImplementation")?.let { cfg ->
        dependencies.add(cfg.name, libs.library("compose-ui-tooling"))
    }
}
