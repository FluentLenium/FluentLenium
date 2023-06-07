import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    id("com.adarshr.test-logger") version "3.2.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

java.targetCompatibility = JavaVersion.VERSION_11
java.sourceCompatibility = JavaVersion.VERSION_11

testlogger {
    showStandardStreams = true
    showPassedStandardStreams = false
    showSkippedStandardStreams = false
    showFailedStandardStreams = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        apiVersion = "1.8"
        languageVersion = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperties(
        mapOf(
            "fluentlenium.capabilities" to
                    """{"goog:chromeOptions": {"args": ["headless=new","no-sandbox", "disable-gpu", "disable-dev-shm-usage"]}}""",
            "java.util.logging.config.file" to "${projectDir}/src/test/resources/logging.properties"
        )
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val fluentleniumVersion = properties["fluentlenium.version"] ?: "6.0.0"
    testImplementation("io.fluentlenium:fluentlenium-kotest:$fluentleniumVersion")
    testImplementation("io.fluentlenium:fluentlenium-kotest-assertions:$fluentleniumVersion")

    val koTestVersion = "5.6.2"
    implementation(platform("io.kotest:kotest-bom:$koTestVersion"))
    implementation(platform("org.junit:junit-bom:5.9.3"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.3")

    val seleniumVersion = properties["selenium.version"] ?: "4.9.1"
    testImplementation("org.seleniumhq.selenium:selenium-api:$seleniumVersion")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
    testRuntimeOnly("org.seleniumhq.selenium:selenium-devtools-v112:$seleniumVersion")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.4")
    testImplementation("org.testcontainers:selenium:1.18.3")

    testImplementation("ch.qos.logback:logback-classic:1.4.7")
    testRuntimeOnly("org.slf4j:jul-to-slf4j:2.0.7")
}

configurations.all {
    exclude(group = "io.netty", module = "netty-transport-native-epoll")
    exclude(group = "io.netty", module = "netty-transport-native-kqueue")
}
