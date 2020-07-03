plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/hotkeytlt/maven")
}

group =  "com.yt8492"
version = "1.0.0"

kotlin {
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