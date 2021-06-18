package org.fluentlenium.adapter.kotest.describespec

import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.TestConstants.PAGE2_URL
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.configuration.FluentConfiguration
import org.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile

@FluentConfiguration(
    driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM,
    configurationDefaults = CustomConfigurationDefault::class,
    webDriver = "chrome"
)
class ConfigurationDefaultSpec : FluentDescribeSpec({
    it("remoteUrl via custom default class") {
        remoteUrl shouldBe getAbsoluteUrlPathFromFile("index.html")
    }

    it("driver should be as defined") {
        webDriver shouldBe "chrome"
    }

    it("should take screenshot") {
        val screenshotPath = tempdir()

        goTo(DEFAULT_URL)
        setScreenshotPath(screenshotPath.path)

        takeScreenshot()

        screenshotPath.list { _, name ->
            name.endsWith(".png")
        } shouldHaveSize 1
    }

    it("should navigate") {
        goTo(DEFAULT_URL)

        driver.navigate().to(PAGE2_URL)
        driver.title shouldContain "Page"
    }
})
