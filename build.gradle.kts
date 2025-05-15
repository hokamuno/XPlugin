plugins {
    java
	id("com.diffplug.spotless") version "6.25.0"
}

group = "ru.azenizzka"
version = "0.0.1-SNAPSHOT"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
}

spotless {
    java {
        googleJavaFormat()
        removeUnusedImports()
    }
}

tasks.named("jar") {
	dependsOn("spotlessApply")
}


group = "ru.azenizzka"
version = "1.0-SNAPSHOT"
description = "XPlugin"