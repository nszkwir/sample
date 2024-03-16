buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

// Lists all plugins used throughout the project without applying them.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    //alias(libs.plugins.roborazzi) apply false
    id("io.github.takahirom.roborazzi") version "1.10.1" apply false
alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
}
