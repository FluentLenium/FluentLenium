package org.fluentlenium.example.kotest

import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentStringSpec
import org.fluentlenium.core.hook.wait.Wait
import org.openqa.selenium.Cookie
import java.io.File
import java.io.FilenameFilter

@Wait
class DuckDuckGoSpec : FluentStringSpec() {

    private val SEARCH_TEXT = "FluentLenium"
    private val PNG_FILTER = FilenameFilter { _, name ->
        name.endsWith(".png")
    }

    override fun getWebDriver(): String = "chrome"

    private fun File.getScreenshots(): List<String> =
        this.list(PNG_FILTER)?.asList() ?: emptyList()

    init {
        "Title of duck duck go" {
            goTo("https://duckduckgo.com")

            el("#search_form_input_homepage").fill().with(SEARCH_TEXT)
            el("#search_button_homepage").submit()
            await().untilWindow(SEARCH_TEXT)

            window().title() shouldContain SEARCH_TEXT
        }

        "Should take screenshot" {
            val tempDirectory = tempdir()

            goTo("https://awesome-testing.com")
            screenshotPath = tempDirectory.absolutePath
            tempDirectory.getScreenshots() shouldHaveSize 0

            takeScreenshot()

            tempDirectory.getScreenshots() shouldHaveAtLeastSize 1
        }

        "Should set cookie" {
            goTo("https://awesome-testing.com")

            driver.manage().addCookie(Cookie("my", "cookie"))
            driver.navigate().refresh()

            getCookie("my").value shouldBe  "cookie"
        }
    }
}