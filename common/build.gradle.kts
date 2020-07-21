import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/hotkeytlt/maven")
}

val ideaActive = System.getProperty("idea.active") == "true"

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    val buildForDevice = project.findProperty("device") as? Boolean ?: false
    fun iosTarget(configure: KotlinNativeTarget.() -> Unit) = if(buildForDevice) {
        iosArm64("ios", configure)
    } else {
        iosX64("ios", configure)
    }

    jvm()
    js {
        browser()
    }
    linuxX64()
    macosX64()
    mingwX64()
    iosTarget {
        binaries {
            framework {
                freeCompilerArgs += "-Xobjc-generics"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.github.h0tk3y.betterParse:better-parse:0.4.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-js"))
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