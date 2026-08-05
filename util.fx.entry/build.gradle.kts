
plugins {
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
}

val appName = project.name.substringAfterLast('.')

repositories {
    mavenCentral()
}

javafx {
    version = "25"
    modules("javafx.controls", "javafx.fxml")
}
