buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // This is where buildscript dependencies would go, if any were explicitly needed here.
        // For example: classpath("com.android.tools.build:gradle:x.y.z")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false // Use the new 2.0 plugin
    alias(libs.plugins.ksp) apply false
}
