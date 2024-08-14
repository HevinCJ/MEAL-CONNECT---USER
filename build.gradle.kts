// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        dependencies {
            classpath(libs.secrets.gradle.plugin)
        }
    }
    dependencies {
        classpath(libs.google.services)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}