package org.fluentlenium.examples.test.componentexample

import org.fluentlenium.core.annotation.Page
import org.fluentlenium.examples.pages.fluentlenium.MainPage
import org.fluentlenium.examples.pages.fluentlenium.QuickStartPage
import org.fluentlenium.examples.test.AbstractFirefoxTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.Dimension

internal class ComponentTest : AbstractFirefoxTest() {

    @Page
    private lateinit var quickStartPage: QuickStartPage

    @Page
    private lateinit var mainPage: MainPage

    @BeforeEach
    fun setUp() {
        driver.manage().window().size = Dimension(1920, 1080)
    }

    @Test
    fun quickStartLink() {
        mainPage.go<MainPage>().isAt()
        mainPage.header.clickQuickstartLink().isAt()
    }

    @Test
    fun homeLink() {
        quickStartPage.go<QuickStartPage>().isAt()
        quickStartPage.header.clickHomeLink().isAt()
    }

    @Test
    fun shouldShowSlawomir() {
        mainPage.go<MainPage>().isAt()
        mainPage.header.clickAboutLink().verifySlawomirPresence()
    }
}