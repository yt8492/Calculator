buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.version}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/hotkeytlt/maven")
    }
}

task<Delete>("rootClean") {
    delete(rootProject.buildDir)
}