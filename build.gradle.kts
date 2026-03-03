plugins {
    java
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.spring.javaformat") version "0.0.43"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    maven {
        name = "Central Portal Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        mavenContent {
            snapshotsOnly()
        }
    }
    maven {
        name = "Spring Milestones"
        url = uri("https://repo.spring.io/milestone")
    }
}

val domaSpringBootVersion = "3.0.0"
val domaVersion = "3.11.0"

dependencies {
    implementation("org.seasar.doma.boot:doma-spring-boot-starter:$domaSpringBootVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-restclient")
    annotationProcessor("org.seasar.doma:doma-processor:$domaVersion")
}

// Doma annotation processor requires SQL files to be in the class output directory
sourceSets.main {
    output.setResourcesDir(output.classesDirs.first())
}

tasks.compileJava {
    dependsOn(tasks.processResources)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
