package io.fluentlenium.examples.test.componentexample

import io.fluentlenium.core.annotation.Page
import io.fluentlenium.examples.pages.fluentlenium.MainPage
import io.fluentlenium.examples.pages.fluentlenium.QuickStartPage
import io.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.openqa.selenium.Dimension

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
internal class QuickStartPageTest : AbstractFirefoxTest() {

    @Page
    private lateinit var onQuickStartPage: QuickStartPage

    @Page
    private lateinit var onMainPage: MainPage

    @BeforeEach
    fun setUp() {
        driver.manage().window().size = Dimension(1920, 1080)
        goTo(onQuickStartPage)
    }

    @Test
    fun homeLink() {
        onQuickStartPage.perform {
            isAt()
            header.clickHomeLink()
        }

        onMainPage.perform {
            isAt()
        }
    }

}