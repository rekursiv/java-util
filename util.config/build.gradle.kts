
plugins { java }


sourceSets {
    main {
        java {
            srcDir("src")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jackson.databind)
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)
}