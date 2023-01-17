plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.0").apply(false)
    id("com.android.library").version("7.4.0").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    kotlin("plugin.serialization").version("1.8.0").apply(false)
    id("com.android.test").version("7.4.0").apply(false)
    id("project-report")
    id("com.vanniktech.dependency.graph.generator").version("0.8.0").apply(false)
    id("org.sonarqube").version("3.3").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
