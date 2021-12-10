package org.fluentlenium.example.kotest

import io.kotest.core.annotation.Ignored
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.Spec
import io.kotest.extensions.testcontainers.perTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentFreeSpec
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.VncRecordingContainer
import java.io.File

/**
 * This test demonstrates how to use Fluentlenium in combination
 * with a Docker/Chrome container (managed by TestContainers)
 * that hosts the browser.
 */
// Testcontainers does not yet work with selenium 4: https://github.com/testcontainers/testcontainers-java/issues/4593
@Ignored
class VideoRecordingTestContainersSpec : FluentFreeSpec() {

    /**
     * directory to save the videos to
     */
    private val videoDirectory = File("build/videos")

    /**
     * start Browser inside Docker container that is also capable of recrding videos
     * https://www.testcontainers.org/modules/webdriver_containers/
     */
    private val dockerChrome = BrowserWebDriverContainer<Nothing>().apply {
        // this is a workaround to avoid kotlin/generics issue:
        // https://github.com/testcontainers/testcontainers-java/issues/318

        withCapabilities(ChromeOptions())
        withRecordingMode(
            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
            videoDirectory,
            VncRecordingContainer.VncRecordingFormat.MP4
        )
    }

    /**
     * use the Kotest testcontainers extension so Kotest can manage the lifecycle of the docker container.
     *
     * https://kotest.io/docs/extensions/test_containers.html
     */
    override fun extensions(): List<Extension> =
        listOf(dockerChrome.perTest())

    override fun beforeSpec(spec: Spec) {
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
