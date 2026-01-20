package com.peto.droidmorning

import com.peto.droidmorning.extentions.androidExtension
import com.peto.droidmorning.extentions.javaVersion
import com.peto.droidmorning.extentions.jvmTarget
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiPlatformAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        androidExtension.apply {
            compileSdk = libs.version("compileSdk").toInt()

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    compilerOptions.jvmTarget.set(libs.jvmTarget("java"))
                }
            }

            defaultConfig {
                minSdk = libs.version("minSdk").toInt()
            }

            compileOptions {
                sourceCompatibility = libs.javaVersion("java")
                targetCompatibility = libs.javaVersion("java")
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            sourceSets {
                getByName("main") {
                    assets.srcDirs("src/androidMain/assets")
                    java.srcDirs("src/androidMain/kotlin")
                    res.srcDirs("src/androidMain/res")
                }
                getByName("test") {
                    assets.srcDirs("src/androidUnitTest/assets")
                    java.srcDirs("src/androidUnitTest/kotlin")
                    res.srcDirs("src/androidUnitTest/res")
                }
            }
        }
    }
}
