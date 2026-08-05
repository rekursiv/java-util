
plugins {
    java
    id("org.gradlex.extra-java-module-info") version "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.google.guice)
}

extraJavaModuleInfo {
    failOnMissingModuleInfo = false
    deriveAutomaticModuleNamesFromFileNames = true
    automaticModule("javax.inject:javax.inject", "java.inject")
}