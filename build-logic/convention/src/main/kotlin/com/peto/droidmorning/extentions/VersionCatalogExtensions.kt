package com.peto.droidmorning.extentions

import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun VersionCatalog.version(name: String): String {
    return findVersion(name).get().requiredVersion
}

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency {
    return findLibrary(name).get().get()
}

internal fun VersionCatalog.plugin(name: String): PluginDependency {
    return findPlugin(name).get().get()
}

internal fun VersionCatalog.bundle(name: String): ExternalModuleDependencyBundle {
    return findBundle(name).get().get()
}

internal fun VersionCatalog.javaVersion(name: String): JavaVersion {
    return JavaVersion.toVersion(version(name))
}

internal fun VersionCatalog.jvmTarget(name: String): JvmTarget {
    return JvmTarget.fromTarget(version(name))
}
