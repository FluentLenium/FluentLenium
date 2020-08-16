package org.fluentlenium.examples.test.componentexample

import org.fluentlenium.core.annotation.Page
import org.fluentlenium.examples.pages.fluentlenium.MainPage
import org.fluentlenium.examples.pages.fluentlenium.QuickStartPage
import org.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.Dimension

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