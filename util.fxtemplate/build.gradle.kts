
plugins {
    id("java")
    application // Required to tell Gradle which class runs the app
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.gradlex.extra-java-module-info") version "1.8"
}

sourceSets {
    main {
        java {
            srcDir("src")
        }
        resources {
            srcDir("src") // Tells Gradle your FXML files live side-by-side with your Java files
            include("**/*.fxml", "**/*.css", "**/*.properties")
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://github.com/poolborges/maven/raw/master/thirdparty/")
    }
}

dependencies {
    implementation(project(":util.config"))
    implementation(project(":util.logging.console"))
    implementation("javax.inject:javax.inject:1")
    implementation(libs.google.guice)
    implementation(libs.google.guava)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)
    implementation(libs.fx.guice)
    implementation(libs.junique)
}

extraJavaModuleInfo {
    failOnMissingModuleInfo = false
    automaticModule("it.sauronsoftware:junique", "junique")
    automaticModule("javax.inject:javax.inject", "java.inject")
    module("com.cathive.fx:fx-guice", "com.cathive.fx.guice") {
        exports("com.cathive.fx.guice")
        requires("com.google.guice")
        requires("java.inject")
        requires("aopalliance")
        requires("javafx.controls")
        requires("javafx.fxml")
    }
}

javafx {
    version = "25"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("util.fxtemplate.Main")
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics")
}
