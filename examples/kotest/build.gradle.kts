import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenLocal()
    mavenCentral()
}

java.targetCompatibility = JavaVersion.VERSION_11
java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
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
                    """{"goog:chromeOptions": {"args": ["headless","no-sandbox", "disable-gpu", "disable-dev-shm-usage"]}}""",
            "java.util.logging.config.file" to "${projectDir}/src/test/resources/logging.properties"
        )
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val fluentleniumVersion = properties["fluentlenium.version"] ?: "6.0.0-SNAPSHOT"
    testImplementation("io.fluentlenium:fluentlenium-kotest:$fluentleniumVersion")
    testImplementation("io.fluentlenium:fluentlenium-kotest-assertions:$fluentleniumVersion")

    val koTestVersion = "5.5.4"
    implementation(platform("io.kotest:kotest-bom:$koTestVersion"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.1")

    val seleniumVersion = "4.7.2"
    testImplementation("org.seleniumhq.selenium:selenium-api:$seleniumVersion")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
    testRuntimeOnly("org.seleniumhq.selenium:selenium-devtools-v107:$seleniumVersion")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.4")
    testImplementation("org.testcontainers:selenium:1.17.6")

    testImplementation("ch.qos.logback:logback-classic:1.4.5")
    testRuntimeOnly("org.slf4j:jul-to-slf4j:2.0.6")
}

configurations.all {
    exclude(group = "io.netty", module = "netty-transport-native-epoll")
    exclude(group = "io.netty", module = "netty-transport-native-kqueue")
}
