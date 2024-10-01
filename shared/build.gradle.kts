import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.8.22"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    val ktorVersion = "3.0.0-rc-1"
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Ktor dependencies for shared code
                implementation(libs.ktor.ktor.client.core)
                implementation(libs.ktor.ktor.client.json)
                implementation(libs.ktor.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.okio) // Use the latest stable version
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor client for Android
                implementation(libs.ktor.ktor.client.okhttp) // You can choose OkHttp or another engine.
              //  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
//        val iosMain by getting {
//            dependencies {
//                // Ktor client for iOS
//                implementation("io.ktor:ktor-client-ios:2.x.x")
//            }
//        }
    }
}

android {
    namespace = "org.jetbrains.greeting.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
dependencies {
    implementation("androidx.room:room-common:2.6.1")
}
