import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import java.util.Properties

plugins {
    alias(libs.plugins.droidmorning.kotlin.multiplatform)
    alias(libs.plugins.droidmorning.android.library)
    alias(libs.plugins.droidmorning.koin)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.bundles.ktor.common)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.bundles.supabase)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

buildkonfig {
    packageName = "com.peto.droidmorning"
    exposeObjectWithName = "BuildKonfig"

    val props =
        Properties().apply {
            val file = rootProject.file("local.properties")
            if (file.exists()) file.inputStream().use { load(it) }
        }

    defaultConfigs {
        buildConfigField(
            Type.STRING,
            "GOOGLE_CLIENT_ID",
            props.getProperty("GOOGLE_CLIENT_ID"),
        )
        buildConfigField(
            Type.STRING,
            "SUPABASE_URL",
            props.getProperty("SUPABASE_URL"),
        )
        buildConfigField(
            Type.STRING,
            "SUPABASE_KEY",
            props.getProperty("SUPABASE_KEY"),
        )
    }
}

android {
    namespace = "com.peto.droidmorning.core.network"
}
