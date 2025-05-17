buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
