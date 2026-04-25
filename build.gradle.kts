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
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}
