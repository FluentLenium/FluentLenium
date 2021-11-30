package org.fluentlenium.example.kotest

import io.kotest.core.Tuple2
import io.kotest.core.spec.AroundTestFn
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestType
import io.kotest.extensions.testcontainers.StartablePerTestListener
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentFreeSpec
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.VncRecordingContainer
import org.testcontainers.lifecycle.Startable
import java.io.File

/**
 * This test demonstrates how to use Fluentlenium in combination with a Docker/Chrome container that hosts the browser.
 */
class VideoRecordingSpec : FluentFreeSpec() {

    private val SEARCH_TEXT = "FluentLenium"

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
    //  override fun extensions(): List<Extension> =
    //    listOf(dockerChrome.perTest())


    override fun newWebDriver(): WebDriver =
        dockerChrome.webDriver

    override fun decorateAround(aroundFn: AroundTestFn): AroundTestFn = { (testcase, runtest) ->
        if (testcase.type == TestType.Test) {
            val listener = StartablePerTestListener(dockerChrome)
            listener.beforeTest(testcase)
            aroundFn(Tuple2(testcase, runtest)).also {
                listener.afterTest(testcase, it)
            }
        } else {
            aroundFn(Tuple2(testcase, runtest))
        }
    }

    override fun beforeSpec(spec: Spec) {
        videoDirectory.mkdirs()
        videoDirectory.exists() shouldBe true
    }

    init {
        aroundTest(dockerChrome.aroundPerTest())

        "Title of duck duck go" {
            goTo("https://duckduckgo.com")

            el("#search_form_input_homepage").fill().with(SEARCH_TEXT)
            el("#search_button_homepage").submit()
            await().untilWindow(SEARCH_TEXT)

            window().title() shouldContain SEARCH_TEXT
        }
    }
}

private fun <T : Startable> T.aroundPerTest(): AroundTestFn = { (testcase, runtest) ->
    if (testcase.type == TestType.Test) {
        val listener = StartablePerTestListener(this)
        listener.beforeTest(testcase)
        runtest(testcase).also {
            listener.afterTest(testcase, it)
        }
    } else {
        runtest(testcase)
    }
}
