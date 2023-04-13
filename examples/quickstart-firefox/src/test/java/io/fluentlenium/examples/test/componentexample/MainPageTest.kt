package io.fluentlenium.examples.test.componentexample

import io.fluentlenium.core.annotation.Page
import io.fluentlenium.examples.pages.fluentlenium.AboutPage
import io.fluentlenium.examples.pages.fluentlenium.MainPage
import io.fluentlenium.examples.pages.fluentlenium.QuickStartPage
import io.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.openqa.selenium.Dimension


@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
internal class MainPageTest : AbstractFirefoxTest() {

    @Page
    private lateinit var onQuickStartPage: QuickStartPage

    @Page
    private lateinit var onMainPage: MainPage

    @Page
    private lateinit var onAboutPage: AboutPage

    @BeforeEach
    fun setUp() {
        driver.manage().window().size = Dimension(1920, 1080)
        goTo(onMainPage)
    }

    @Test
    fun quickStartLink() {
        onMainPage.perform {
            isAt()
            header.clickQuickstartLink()
        }

        onQuickStartPage.perform {
            isAt()
        }
    }

    @Test
    fun shouldShowSlawomir() {
        onMainPage.perform {
            isAt()
            header.clickAboutLink()
        }

        onAboutPage.perform {
            verifySlawomirPresence()
        }
    }
}