
plugins {
    java
    id("org.gradlex.extra-java-module-info") version "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":util.config"))
    implementation("javax.inject:javax.inject:1")
    implementation(libs.google.guice)
    implementation(libs.jackson.databind)
//    implementation(libs.jackson.core)
//    implementation(libs.jackson.annotations)
}

extraJavaModuleInfo {
    failOnMissingModuleInfo = false
    deriveAutomaticModuleNamesFromFileNames = true
    automaticModule("javax.inject:javax.inject", "java.inject")
}