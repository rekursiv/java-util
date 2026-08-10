
plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":util.config"))
    implementation(libs.google.guice)
    implementation(libs.jackson.databind)
}
