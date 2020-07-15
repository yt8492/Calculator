plugins {
    id("org.jetbrains.kotlin.js")
}


repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":common"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.110-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.110-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.110-kotlin-1.3.72")
}

kotlin.target.browser {
    webpackTask {
        outputFileName = "main.js"
    }
    runTask {
        outputFileName = "main.js"
    }
}