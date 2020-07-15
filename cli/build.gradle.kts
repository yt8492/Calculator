plugins {
    id("org.jetbrains.kotlin.multiplatform")
}
repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
}
kotlin {
    macosX64("macos") {
        binaries {
            executable {
                entryPoint = "com.yt8492.calculator.cli.main"
                runTask?.args("")
            }
        }
    }
    linuxX64() {
        binaries {
            executable {
                entryPoint = "com.yt8492.calculator.cli.main"
                runTask?.args("")
            }
        }
    }
    mingwX64() {
        binaries {
            executable {
                entryPoint = "com.yt8492.calculator.cli.main"
                runTask?.args("")
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-cli:0.2.1")
            }
        }
    }
}
