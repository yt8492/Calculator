plugins {
    kotlin("multiplatform") version "1.3.72"
}

group = "com.yt8492"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/hotkeytlt/maven")
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.github.h0tk3y.betterParse:better-parse-metadata:0.4.0-alpha-3")
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