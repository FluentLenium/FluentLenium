import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
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
        apiVersion = "1.6"
        languageVersion = "1.6"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperties(
        mapOf(
            "fluentlenium.capabilities" to
                    """{"chromeOptions": {"args": ["headless","no-sandbox", "disable-gpu", "disable-dev-shm-usage"]}}""",
            "java.util.logging.config.file" to "${projectDir}/src/test/resources/logging.properties"
        )
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val fluentleniumVersion = "4.8.1-SNAPSHOT"
    testImplementation("org.fluentlenium:fluentlenium-kotest:$fluentleniumVersion")
    testImplementation("org.fluentlenium:fluentlenium-kotest-assertions:$fluentleniumVersion")

    val koTestVersion = "5.0.2"
    implementation(platform("io.kotest:kotest-bom:$koTestVersion"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.0.3")
    testImplementation("org.seleniumhq.selenium:selenium-api:4.1.0")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:4.1.0")
    testRuntimeOnly("org.seleniumhq.selenium:selenium-devtools-v95:4.1.0")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.1.0")
    testImplementation("org.testcontainers:selenium:1.16.2")

    testImplementation("ch.qos.logback:logback-classic:1.2.8")
    testRuntimeOnly("org.slf4j:jul-to-slf4j:1.7.32")
}

configurations.all {
    exclude(group = "io.netty", module = "netty-transport-native-epoll")
    exclude(group = "io.netty", module = "netty-transport-native-kqueue")
}