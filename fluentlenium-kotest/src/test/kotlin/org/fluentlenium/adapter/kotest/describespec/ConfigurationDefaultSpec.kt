package org.fluentlenium.adapter.kotest.describespec

import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.configuration.FluentConfiguration
import org.openqa.selenium.Cookie

@FluentConfiguration(
    driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM,
    configurationDefaults = CustomConfigurationDefault::class
)
class ConfigurationDefaultSpec : FluentDescribeSpec({
    it("remoteUrl via custom default class") {
        remoteUrl shouldBe "https://www.google.com"
    }

    it("should take screenshot") {
        val screenshotPath = tempdir()

        goTo("https://awesome-testing.com")
        setScreenshotPath(screenshotPath.path)

        takeScreenshot()

        screenshotPath.list { _, name ->
            name.endsWith(".png")
        } shouldHaveSize 1
    }

    it("should set cookie") {
        goTo("https://awesome-testing.com")

        driver.manage().addCookie(Cookie("my", "cookie"))
        driver.navigate().refresh()

        getCookie("my").value shouldBe "cookie"
    }
})
