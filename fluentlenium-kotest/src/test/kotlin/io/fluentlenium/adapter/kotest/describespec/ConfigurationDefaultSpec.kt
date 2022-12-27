package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.TestConstants.PAGE2_URL
import io.fluentlenium.configuration.ConfigurationProperties
import io.fluentlenium.configuration.FluentConfiguration
import io.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

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
