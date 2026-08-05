
plugins {
    java
    id("org.javamodularity.moduleplugin") version "1.8.15"
}

dependencies {
    implementation(files("../../javalib/org.ektorp-1.5.0.jar"))
}
