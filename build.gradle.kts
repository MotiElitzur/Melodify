// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.gradle) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.maven.publish.plugin) apply false
    alias(libs.plugins.nmcp) apply false
}