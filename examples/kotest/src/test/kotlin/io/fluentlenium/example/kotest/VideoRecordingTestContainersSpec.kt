package io.fluentlenium.example.kotest

import io.fluentlenium.adapter.kotest.FluentFreeSpec
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec
import io.kotest.extensions.testcontainers.TestContainerExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.VncRecordingContainer

/**
 * This test demonstrates how to use Fluentlenium in combination
 * with a Docker/Chrome container (managed by TestContainers)
 * that hosts the browser.
 */
@EnabledIf(IsNotMacOS::class)
class VideoRecordingTestContainersSpec : FluentFreeSpec() {

    /**
     * directory to save the videos to
     */
    private val videoDirectory = File("build/videos")

    /**
     * start Browser inside Docker container that is also capable of recording videos
     * https://www.testcontainers.org/modules/webdriver_containers/
     */
    private val dockerChrome = install(TestContainerExtension(BrowserWebDriverContainer())) {
        withCapabilities(ChromeOptions().apply {
            addArguments("--headless=new")
        })
        withRecordingMode(
            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
            videoDirectory,
            VncRecordingContainer.VncRecordingFormat.MP4
        )
    }

    override suspend fun beforeSpec(spec: Spec) {
        videoDirectory.mkdirs()
        videoDirectory.exists() shouldBe true
    }

    override fun newWebDriver(): WebDriver =
        dockerChrome.webDriver

    private val SEARCH_TEXT = "FluentLenium"

    init {
        "Title of duck duck go" {
            goTo("https://duckduckgo.com")

            el("#search_form_input_homepage").fill().with(SEARCH_TEXT)
            el("#search_button_homepage").submit()
            await().untilWindow(SEARCH_TEXT)

            window().title() shouldContain SEARCH_TEXT
        }
    }
}
