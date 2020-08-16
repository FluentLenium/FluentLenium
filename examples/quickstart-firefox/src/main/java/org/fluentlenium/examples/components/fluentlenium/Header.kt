package org.fluentlenium.examples.components.fluentlenium

import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.examples.pages.fluentlenium.AboutPage
import org.fluentlenium.examples.pages.fluentlenium.MainPage
import org.fluentlenium.examples.pages.fluentlenium.QuickStartPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class Header(element: WebElement?, control: FluentControl?, instantiator: ComponentInstantiator?) :
        FluentWebElement(element, control, instantiator) {

    @FindBy(css = "nav ul li:nth-of-type(1)")
    private lateinit var homeLink: FluentWebElement

    @FindBy(css = "nav ul li:nth-of-type(2)")
    private lateinit var quickstartLink: FluentWebElement

    @FindBy(css = "nav ul li:nth-of-type(5)")
    private lateinit var aboutLink: FluentWebElement

    fun clickHomeLink(): MainPage {
        homeLink.click()
        return newInstance(MainPage::class.java)
    }

    fun clickQuickstartLink(): QuickStartPage {
        quickstartLink.click()
        return newInstance(QuickStartPage::class.java)
    }

    fun clickAboutLink(): AboutPage {
        aboutLink.click()
        return newInstance(AboutPage::class.java)
    }
}