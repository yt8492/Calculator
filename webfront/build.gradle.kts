plugins {
    id("org.jetbrains.kotlin.js")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin.target.browser {
    webpackTask {
        outputFileName = "main.js"
    }
    runTask {
        outputFileName = "main.js"
    }
}