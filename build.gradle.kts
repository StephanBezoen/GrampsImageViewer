val libs = libraries
val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.1").apply(false)
    id("com.android.library").version("7.4.1").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
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
