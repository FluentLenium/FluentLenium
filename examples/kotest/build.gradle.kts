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
    testImplementation("org.fluentlenium:fluentlenium-kotest:4.6.3-SNAPSHOT")
    testImplementation("org.fluentlenium:fluentlenium-kotest-assertions:4.6.3-SNAPSHOT")

    val koTestVersion = "4.6.0"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")

    testImplementation("io.github.bonigarcia:webdrivermanager:4.4.3")
    testImplementation("org.seleniumhq.selenium:selenium-api:3.141.59")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:3.141.59")
}