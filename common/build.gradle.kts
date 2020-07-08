plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/hotkeytlt/maven")
}

androidCommon()
android {
    sourceSets.forEach {
        it.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    android("android")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.github.h0tk3y.betterParse:better-parse:0.4.0")

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Kotlin.jvm)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}