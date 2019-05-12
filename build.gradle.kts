import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"

    kotlin("plugin.noarg") version "1.3.11"
    kotlin("plugin.allopen") version "1.3.11"

    id("io.spring.dependency-management") version "1.0.4.RELEASE"
    id("org.springframework.boot") version "2.1.1.RELEASE"
}

group = "org.droidwiki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    val springBootVersion = "2.1.1.RELEASE"
    imports {
        mavenBom("org.springframework.boot:spring-boot-parent:$springBootVersion")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.jsoup:jsoup:1.11.3")

    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit")
    }
}

sourceSets {
    create("integrationTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/integrationTest/kotlin")
            resources.srcDir("src/integrationTest/resources")
            compileClasspath += sourceSets["main"].output + sourceSets["test"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

task<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "STARTED", "FAILED", "SKIPPED")
        }
    }

    "check" {
        dependsOn("integrationTest")
    }
}
