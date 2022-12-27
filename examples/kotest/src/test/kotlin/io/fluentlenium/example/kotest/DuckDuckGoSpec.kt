package io.fluentlenium.example.kotest

import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentFreeSpec
import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait
import io.fluentlenium.kotest.matchers.el.haveClass
import io.fluentlenium.kotest.matchers.el.haveDimension
import io.fluentlenium.kotest.matchers.el.shouldHaveDimension
import io.fluentlenium.kotest.matchers.jq.haveTagName
import io.fluentlenium.kotest.matchers.jq.shouldHaveTagName
import org.openqa.selenium.Cookie
import java.io.File
import java.io.FilenameFilter
import io.fluentlenium.kotest.matchers.jq.haveClass as listHaveClass

@Wait
class DuckDuckGoSpec : FluentFreeSpec() {

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

            getCookie("my").value shouldBe "cookie"
        }

        "Kotest assertion" - {
            "can use extension functions" {
                goTo("https://awesome-testing.com")

                jq(".post-title").shouldHaveTagName("h1")
                el("img#Header1_headerimg").shouldHaveDimension(1000 to 402)
            }

            "alternatively can use infix syntax" {
                goTo("https://awesome-testing.com")

                jq(".post-title") should haveTagName("h1")
                el("img#Header1_headerimg") should haveDimension(1000 to 402)
            }

            "infix matchers can be aliased to to prevent name ambiguities" {
                goTo("https://awesome-testing.com")

                el(".post-title") should haveClass("post-title")
                jq(".post-title") should listHaveClass("post-title")
            }
        }
    }
}