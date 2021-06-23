import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

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

    val koTestVersion = "4.6.0"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")

    testImplementation("io.github.bonigarcia:webdrivermanager:4.4.3")
    testImplementation("org.seleniumhq.selenium:selenium-api:4.0.0-beta-4")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0-beta-4")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.0.0")
    testImplementation("org.testcontainers:selenium:1.15.3")

    testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

configurations.all {
    exclude(group = "io.netty", module = "netty-transport-native-epoll")
    exclude(group = "io.netty", module = "netty-transport-native-kqueue")
}