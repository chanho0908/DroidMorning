package com.peto.droidmorning

import com.peto.droidmorning.extentions.androidExtension
import com.peto.droidmorning.extentions.libs
import com.peto.droidmorning.extentions.version
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Android 타겟 설정 플러그인
 */
class KotlinMultiPlatformAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        androidExtension.apply {
            compileSdk = libs.version("compileSdk").toInt()

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
                }
            }

            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
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
