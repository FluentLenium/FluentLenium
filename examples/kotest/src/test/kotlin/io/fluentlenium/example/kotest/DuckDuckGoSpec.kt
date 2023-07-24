package io.fluentlenium.example.kotest

import io.fluentlenium.adapter.kotest.FluentFreeSpec
import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait
import io.fluentlenium.kotest.matchers.el.containText
import io.fluentlenium.kotest.matchers.el.haveClass
import io.fluentlenium.kotest.matchers.el.shouldContainText
import io.fluentlenium.kotest.matchers.jq.haveTagName
import io.fluentlenium.kotest.matchers.jq.shouldHaveTagName
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.File
import java.io.FilenameFilter
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Cookie
import org.openqa.selenium.chrome.ChromeOptions
import io.fluentlenium.kotest.matchers.jq.haveClass as listHaveClass

@Wait
class DuckDuckGoSpec : FluentFreeSpec() {

    private val SEARCH_TEXT = "FluentLenium"
    private val PNG_FILTER = FilenameFilter { _, name ->
        name.endsWith(".png")
    }

    override fun getWebDriver(): String = "chrome"

    override fun getCapabilities(): Capabilities {
        return ChromeOptions().apply {
            addArguments("--headless=new")
        }
    }

    private fun File.getScreenshots(): List<String> =
        this.list(PNG_FILTER)?.asList() ?: emptyList()

    init {
        "Title of duck duck go" {
            goTo("https://duckduckgo.com")

            el("#searchbox_input").fill().with(SEARCH_TEXT)
            el("button[type=submit]").submit()
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
                goTo("https://fluentlenium.io/quickstart/")

                jq(".highlight").shouldHaveTagName("div")
                el("h1").shouldContainText("Quick Start")
            }

            "alternatively can use infix syntax" {
                goTo("https://fluentlenium.io/quickstart/")

                jq(".highlight") should haveTagName("div")
                el("h1") should containText("Quick Start")
            }

            "infix matchers can be aliased to to prevent name ambiguities" {
                goTo("https://fluentlenium.io/quickstart/")

                el(".language-java") should haveClass("highlighter-rouge")
                jq(".language-java") should listHaveClass("highlighter-rouge")
            }
        }
    }
}