val libs = libraries
val versionCatalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.application").version("8.0.2").apply(false)
    id("com.android.library").version("8.0.2").apply(false)
    kotlin("android").version("1.8.10").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    kotlin("plugin.serialization").version("1.8.0").apply(false)
    id("com.android.test").version("7.4.0").apply(false)
    id("project-report")
    id("com.vanniktech.dependency.graph.generator").version("0.8.0").apply(false)

    id("org.sonarqube").version("3.5.0.2730").apply(true)
}
sonar {
    properties {
        property("sonar.projectName", "Image Viewer")
        property(
            "sonar.exclusions",
            "**/databinding/**/*.*, **/android/databinding/*Binding.*,**/BR.*, **/R.*, **/R$*.*, **/BuildConfig.*, **/Manifest*.*, **/R.java, **/R\\$*.java"
        )
        property(
            "sonar.coverage.exclusions",
            "**/databinding/**/*.*,**/android/databinding/*Binding.*,**/BR.*,**/R.*,**/R$*.*,**/BuildConfig.*,**/Manifest*.*,**/*Module_*Factory.*"
        )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
