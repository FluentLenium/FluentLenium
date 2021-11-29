import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

java.targetCompatibility = JavaVersion.VERSION_1_8
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.5"
        languageVersion = "1.5"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-progressive")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperty(
        "fluentlenium.capabilities",
        """{"chromeOptions": {"args": ["headless","no-sandbox", "disable-gpu", "disable-dev-shm-usage"]}}"""
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    val fluentleniumVersion = "4.8.1-SNAPSHOT"
    testImplementation("org.fluentlenium:fluentlenium-kotest:$fluentleniumVersion")
    testImplementation("org.fluentlenium:fluentlenium-kotest-assertions:$fluentleniumVersion")

    val koTestVersion = "5.0.0"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.0.3")
    testImplementation("org.seleniumhq.selenium:selenium-api:3.141.59")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:3.141.59")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.0.1")
    testImplementation("org.testcontainers:selenium:1.16.0")

    testImplementation("ch.qos.logback:logback-classic:1.2.6")
}
