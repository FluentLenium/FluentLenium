package org.fluentlenium.adapter.spock.control

import org.fluentlenium.adapter.FluentControlContainer
import org.fluentlenium.adapter.spock.SpockControl
import org.fluentlenium.adapter.spock.page.Page2
import org.fluentlenium.configuration.Configuration
import org.fluentlenium.configuration.DefaultConfigurationFactory
import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.Component
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.search.SearchFilter
import org.openqa.selenium.By
import org.openqa.selenium.Capabilities
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeOptions
import spock.lang.Specification

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.METHOD
import static org.fluentlenium.configuration.ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL

class SpockControlUnitSpec extends Specification {

    FluentControl mockFluentControl = Mock()

    Configuration mockConfiguration = Mock()
    FluentControlContainer mockFluentControlContainer = Mock() {
        getFluentControl() >> mockFluentControl
    }
    SpockControl spockControl = new SpockControl(
            configuration: mockConfiguration,
            controlContainer: mockFluentControlContainer)

    def 'should call getDriver'() {
        when:
        spockControl.getDriver()

        then:
        1 * mockFluentControl.getDriver()
    }

    def 'should call getAppiumDriver'() {
        when:
        spockControl.getAppiumDriver()

        then:
        1 * mockFluentControl.getAppiumDriver()
    }

    def 'should call performanceTiming'() {
        when:
        spockControl.performanceTiming()

        then:
        1 * mockFluentControl.performanceTiming()
    }

    def 'should call get/setWebDriver'() {
        when:
        spockControl.setWebDriver("chrome")
        spockControl.getWebDriver()

        then:
        1 * mockConfiguration.setWebDriver("chrome")
        1 * mockConfiguration.getWebDriver()
    }

    def 'should call get/setBrowserTimeout'() {
        when:
        spockControl.setBrowserTimeout(2L)
        spockControl.getBrowserTimeout()

        then:
        1 * mockConfiguration.setBrowserTimeout(2L)
        1 * mockConfiguration.getBrowserTimeout()
    }

    def 'should call get/setBrowserTimeoutRetries'() {
        when:
        spockControl.setBrowserTimeoutRetries(2)
        spockControl.getBrowserTimeoutRetries()

        then:
        1 * mockConfiguration.setBrowserTimeoutRetries(2)
        1 * mockConfiguration.getBrowserTimeoutRetries()
    }

    def 'should call get/setRemoteUrl'() {
        when:
        spockControl.setRemoteUrl("https://fluentlenium.com")
        spockControl.getRemoteUrl()

        then:
        1 * mockConfiguration.setRemoteUrl("https://fluentlenium.com")
        1 * mockConfiguration.getRemoteUrl()
    }

    def 'should call get/setCapabilities'() {
        given:
        Capabilities capabilities = new ChromeOptions()

        when:
        spockControl.setCapabilities(capabilities)
        spockControl.getCapabilities()

        then:
        1 * mockConfiguration.setCapabilities(capabilities)
        1 * mockConfiguration.getCapabilities()
    }

    def 'should call getConfigurationDefaults'() {
        when:
        spockControl.getConfigurationDefaults()

        then:
        1 * mockConfiguration.getConfigurationDefaults()
    }

    def 'should call get/setConfigurationFactory'() {
        when:
        spockControl.setConfigurationFactory(DefaultConfigurationFactory.class)
        spockControl.getConfigurationFactory()

        then:
        1 * mockConfiguration.setConfigurationFactory(DefaultConfigurationFactory.class)
        1 * mockConfiguration.getConfigurationFactory()
    }

    def 'should call get/setDriverLifecycle'() {
        given:
        def driverLifecycle = METHOD

        when:
        spockControl.setDriverLifecycle(driverLifecycle)
        spockControl.getDriverLifecycle()

        then:
        1 * mockConfiguration.setDriverLifecycle(driverLifecycle)
        1 * mockConfiguration.getDriverLifecycle()
    }

    def 'should call get/setDeleteCookies'() {
        when:
        spockControl.setDeleteCookies(false)
        spockControl.getDeleteCookies()

        then:
        1 * mockConfiguration.setDeleteCookies(false)
        1 * mockConfiguration.getDeleteCookies()
    }

    def 'should call get/setBaseUrl'() {
        when:
        spockControl.setBaseUrl("web")
        spockControl.getBaseUrl()

        then:
        1 * mockConfiguration.setBaseUrl("web")
        1 * mockConfiguration.getBaseUrl()
    }

    def 'should call get/setPageLoadTimeout'() {
        when:
        spockControl.setPageLoadTimeout(1L)
        spockControl.getPageLoadTimeout()

        then:
        1 * mockConfiguration.setPageLoadTimeout(1L)
        1 * mockConfiguration.getPageLoadTimeout()
    }

    def 'should call get/setImplicitlyWait'() {
        when:
        spockControl.setImplicitlyWait(1L)
        spockControl.getImplicitlyWait()

        then:
        1 * mockConfiguration.setImplicitlyWait(1L)
        1 * mockConfiguration.getImplicitlyWait()
    }

    def 'should call get/setAwaitAtMost'() {
        when:
        spockControl.setAwaitAtMost(1L)
        spockControl.getAwaitAtMost()

        then:
        1 * mockConfiguration.setAwaitAtMost(1L)
        1 * mockConfiguration.getAwaitAtMost()
    }

    def 'should call get/setAwaitPollingEvery'() {
        when:
        spockControl.setAwaitPollingEvery(2L)
        spockControl.getAwaitPollingEvery()

        then:
        1 * mockConfiguration.setAwaitPollingEvery(2L)
        1 * mockConfiguration.getAwaitPollingEvery()
    }

    def 'should call get/setScriptTimeout'() {
        when:
        spockControl.setScriptTimeout(3L)
        spockControl.getScriptTimeout()

        then:
        1 * mockConfiguration.setScriptTimeout(3L)
        1 * mockConfiguration.getScriptTimeout()
    }

    def 'should call get/setEventsEnabled'() {
        when:
        spockControl.setEventsEnabled(true)
        spockControl.getEventsEnabled()

        then:
        1 * mockConfiguration.setEventsEnabled(true)
        1 * mockConfiguration.getEventsEnabled()
    }

    def 'should call get/setScreenshotPath'() {
        when:
        spockControl.setScreenshotPath("/tmp")
        spockControl.getScreenshotPath()

        then:
        1 * mockConfiguration.setScreenshotPath("/tmp")
        1 * mockConfiguration.getScreenshotPath()
    }

    def 'should call get/setScreenshotMode'() {
        given:
        def triggerMode = AUTOMATIC_ON_FAIL

        when:
        spockControl.setScreenshotMode(triggerMode)
        spockControl.getScreenshotMode()

        then:
        1 * mockConfiguration.setScreenshotMode(triggerMode)
        1 * mockConfiguration.getScreenshotMode()
    }

    def 'should call get/setHtmlDumpPath'() {
        when:
        spockControl.setHtmlDumpPath("/tmp")
        spockControl.getHtmlDumpPath()

        then:
        1 * mockConfiguration.setHtmlDumpPath("/tmp")
        1 * mockConfiguration.getHtmlDumpPath()
    }

    def 'should call get/setHtmlDumpMode'() {
        given:
        def triggerMode = AUTOMATIC_ON_FAIL

        when:
        spockControl.setHtmlDumpMode(triggerMode)
        spockControl.getHtmlDumpMode()

        then:
        1 * mockConfiguration.setHtmlDumpMode(triggerMode)
        1 * mockConfiguration.getHtmlDumpMode()
    }

    def 'should call setCustomProperty'() {
        when:
        spockControl.setCustomProperty("key", "value")
        spockControl.getCustomProperty("key")

        then:
        1 * mockConfiguration.setCustomProperty("key", "value")
        1 * mockConfiguration.getCustomProperty("key")
    }

    def 'should call keyboard'() {
        when:
        spockControl.keyboard()

        then:
        1 * mockFluentControl.keyboard()
    }

    def 'should call mouse'() {
        when:
        spockControl.mouse()

        then:
        1 * mockFluentControl.mouse()
    }

    def 'should call alert'() {
        when:
        spockControl.alert()

        then:
        1 * mockFluentControl.alert()
    }

    def 'should call capabilities'() {
        when:
        spockControl.capabilities()

        then:
        1 * mockFluentControl.capabilities()
    }

    def 'should call newFluent'() {
        given:
        def element = Mock(WebElement)

        when:
        spockControl.newFluent(element)

        then:
        1 * mockFluentControl.newFluent(element)
    }

    def 'should call newComponent'() {
        given:
        def clazz = Object.class
        def element = Mock(WebElement)

        when:
        spockControl.newComponent(clazz, element)

        then:
        1 * mockFluentControl.newComponent(clazz, element)
    }

    def 'should call newFluentList - 1/6'() {
        when:
        spockControl.newFluentList()

        then:
        1 * mockFluentControl.newFluentList()
    }

    def 'should call newFluentList - 2/6'() {
        given:
        FluentWebElement element = Mock(FluentWebElement)

        when:
        spockControl.newFluentList(element)

        then:
        1 * mockFluentControl.newFluentList(element)
    }

    def 'should call newFluentList - 3/6'() {
        given:
        List<FluentWebElement> list = Mock(List<FluentWebElement>)

        when:
        spockControl.newFluentList(list)

        then:
        1 * mockFluentControl.newFluentList(list)
    }

    def 'should call newFluentList - 4/6'() {
        given:
        def clazz = Object.class

        when:
        spockControl.newFluentList(clazz)

        then:
        1 * mockFluentControl.newFluentList(clazz)
    }

    def 'should call newFluentList - 5/6'() {
        given:
        def clazz = FluentWebElement.class
        FluentWebElement element = Mock(FluentWebElement)

        when:
        spockControl.newFluentList(clazz, element)

        then:
        1 * mockFluentControl.newFluentList(clazz, element)
    }

    def 'should call newFluentList - 6/6'() {
        given:
        def clazz = Object.class
        List<WebElement> list = Mock(List<WebElement>)

        when:
        spockControl.newFluentList(clazz, list)

        then:
        1 * mockFluentControl.newFluentList(clazz, list)
    }

    def 'should call asFluentList - 1/6'() {
        given:
        def element = Mock(WebElement)

        when:
        spockControl.asFluentList(element)

        then:
        1 * mockFluentControl.asFluentList(element)
    }

    def 'should call asFluentList - 2/6'() {
        given:
        Iterable<WebElement> iterable = Mock(Iterable<WebElement>)

        when:
        spockControl.asFluentList(iterable)

        then:
        1 * mockFluentControl.asFluentList(iterable)
    }

    def 'should call asFluentList - 3/6'() {
        given:
        List<WebElement> list = Mock(List<WebElement>)

        when:
        spockControl.asFluentList(list)

        then:
        1 * mockFluentControl.asFluentList(list)
    }

    def 'should call asFluentList - 4/6'() {
        given:
        def clazz = Object.class
        def element = Mock(WebElement)

        when:
        spockControl.asFluentList(clazz, element)

        then:
        1 * mockFluentControl.asFluentList(clazz, element)
    }

    def 'should call asFluentList - 5/6'() {
        given:
        def clazz = Object.class
        Iterable<WebElement> iterable = Mock(Iterable<WebElement>)

        when:
        spockControl.asFluentList(clazz, iterable)

        then:
        1 * mockFluentControl.asFluentList(clazz, iterable)
    }

    def 'should call asFluentList - 6/6'() {
        given:
        def clazz = Object.class
        List<WebElement> list = Mock(List<WebElement>)

        when:
        spockControl.asFluentList(clazz, list)

        then:
        1 * mockFluentControl.asFluentList(clazz, list)
    }

    def 'should call newComponentList - 1/6'() {
        given:
        def clazz = Object.class

        when:
        spockControl.newComponentList(clazz)

        then:
        1 * mockFluentControl.newComponentList(clazz)
    }

    def 'should call newComponentList - 2/6'() {
        given:
        def clazz = FluentWebElement.class
        FluentWebElement element = Mock(FluentWebElement)

        when:
        spockControl.newComponentList(clazz, element)

        then:
        1 * mockFluentControl.newComponentList(clazz, element)
    }

    def 'should call newComponentList - 3/6'() {
        given:
        def clazz = FluentWebElement.class
        List<FluentWebElement> list = Mock(List<FluentWebElement>)

        when:
        spockControl.newComponentList(clazz, list)

        then:
        1 * mockFluentControl.newComponentList(clazz, list)
    }

    def 'should call newComponentList - 4/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class

        when:
        spockControl.newComponentList(objectClazz, componentClazz)

        then:
        1 * mockFluentControl.newComponentList(objectClazz, componentClazz)
    }

    def 'should call newComponentList - 5/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class
        FluentWebElement element = Mock(FluentWebElement)

        when:
        spockControl.newComponentList(objectClazz, componentClazz, element)

        then:
        1 * mockFluentControl.newComponentList(objectClazz, componentClazz, element)
    }

    def 'should call newComponentList - 6/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class
        List<FluentWebElement> list = Mock(List<FluentWebElement>)

        when:
        spockControl.newComponentList(objectClazz, componentClazz, list)

        then:
        1 * mockFluentControl.newComponentList(objectClazz, componentClazz, list)
    }

    def 'should call asComponentList - 1/6'() {
        given:
        def objectClazz = FluentWebElement.class
        WebElement element = Mock(WebElement)

        when:
        spockControl.asComponentList(objectClazz, element)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, element)
    }

    def 'should call asComponentList - 2/6'() {
        given:
        def objectClazz = FluentWebElement.class
        Iterable<WebElement> iterable = Mock(Iterable<WebElement>)

        when:
        spockControl.asComponentList(objectClazz, iterable)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, iterable)
    }

    def 'should call asComponentList - 3/6'() {
        given:
        def objectClazz = FluentWebElement.class
        List<WebElement> list = Mock(List<WebElement>)

        when:
        spockControl.asComponentList(objectClazz, list)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, list)
    }

    def 'should call asComponentList - 4/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class
        WebElement element = Mock(WebElement)

        when:
        spockControl.asComponentList(objectClazz, componentClazz, element)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, componentClazz, element)
    }

    def 'should call asComponentList - 5/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class
        Iterable<WebElement> iterable = Mock(Iterable<WebElement>)

        when:
        spockControl.asComponentList(objectClazz, componentClazz, iterable)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, componentClazz, iterable)
    }

    def 'should call asComponentList - 6/6'() {
        given:
        def objectClazz = FluentWebElement.class
        def componentClazz = Component.class
        List<WebElement> list = Mock(List<WebElement>)

        when:
        spockControl.asComponentList(objectClazz, componentClazz, list)

        then:
        1 * mockFluentControl.asComponentList(objectClazz, componentClazz, list)
    }

    def 'should call isComponentClass / isComponentListClass'() {
        given:
        def clazz = Component.class

        when:
        spockControl.isComponentClass(clazz)
        spockControl.isComponentListClass(clazz)

        then:
        1 * mockFluentControl.isComponentClass(clazz)
        1 * mockFluentControl.isComponentListClass(clazz)

    }

    def 'should call css'() {
        when:
        spockControl.css()

        then:
        1 * mockFluentControl.css()
    }

    def 'should call events'() {
        when:
        spockControl.events()

        then:
        1 * mockFluentControl.events()
    }

    def 'should call inject'() {
        given:
        def object = new Object()

        when:
        spockControl.inject(object)

        then:
        1 * mockFluentControl.inject(object)
    }

    def 'should call injectComponent'() {
        given:
        def object = new Object()
        def parentContainer = new Object()
        SearchContext searchContext = Mock(SearchContext)

        when:
        spockControl.injectComponent(object, parentContainer, searchContext)

        then:
        1 * mockFluentControl.injectComponent(object, parentContainer, searchContext)
    }

    def 'should call newInstance'() {
        given:
        def page = Page2.class

        when:
        spockControl.newInstance(page)

        then:
        1 * mockFluentControl.newInstance(page)
    }

    def 'should call goTo'() {
        given:
        FluentPage page = Mock(FluentPage)
        def url = "url.com"

        when:
        spockControl.goTo(page)
        spockControl.goTo(url)
        spockControl.goToInNewTab(url)

        then:
        1 * mockFluentControl.goTo(page)
        1 * mockFluentControl.goTo(url)
        1 * mockFluentControl.goToInNewTab(url)
    }

    def 'should call switchTo'() {
        given:
        FluentWebElement element = Mock(FluentWebElement)
        FluentList<FluentWebElement> list = Mock(FluentList<FluentWebElement>)

        when:
        spockControl.switchTo()
        spockControl.switchToDefault()
        spockControl.switchTo(element)
        spockControl.switchTo(list)

        then:
        1 * mockFluentControl.switchTo()
        1 * mockFluentControl.switchToDefault()
        1 * mockFluentControl.switchTo(element)
        1 * mockFluentControl.switchTo(list)
    }

    def 'should call pageSource'() {
        when:
        spockControl.pageSource()

        then:
        1 * mockFluentControl.pageSource()
    }

    def 'should call window'() {
        when:
        spockControl.window()

        then:
        1 * mockFluentControl.window()
    }

    def 'should call getCookies'() {
        when:
        spockControl.getCookies()
        spockControl.getCookie("name")

        then:
        1 * mockFluentControl.getCookies()
        1 * mockFluentControl.getCookie("name")
    }

    def 'should call url'() {
        when:
        spockControl.url()

        then:
        1 * mockFluentControl.url()
    }

    def 'should call executeScript'() {
        given:
        def script = ""
        def args = ""

        when:
        spockControl.executeScript(script, args)
        spockControl.executeAsyncScript(script, args)

        then:
        1 * mockFluentControl.executeScript(script, args)
        1 * mockFluentControl.executeAsyncScript(script, args)
    }

    def 'should call el'() {
        given:
        WebElement element = Mock(WebElement)

        when:
        spockControl.el(element)

        then:
        1 * mockFluentControl.el(element)
    }

    def 'should call find'() {
        given:
        List<WebElement> list = Mock(List<WebElement>)
        SearchFilter searchFilter = Mock(SearchFilter)
        By by = Mock(By)
        def selector = ""

        when:
        spockControl.find(list)
        spockControl.find(selector, searchFilter)
        spockControl.find(searchFilter)
        spockControl.find(by, searchFilter)

        then:
        1 * mockFluentControl.find(list)
        1 * mockFluentControl.find(selector, searchFilter)
        1 * mockFluentControl.find(searchFilter)
        1 * mockFluentControl.find(by, searchFilter)
    }

    def 'should call htmlDump'() {
        given:
        def fileName = ""

        when:
        spockControl.takeHtmlDump()
        spockControl.takeHtmlDump(fileName)

        then:
        1 * mockFluentControl.takeHtmlDump()
        1 * mockFluentControl.takeHtmlDump(fileName)
    }

    def 'should call screenshot'() {
        given:
        def fileName = ""

        when:
        spockControl.takeScreenshot()
        spockControl.takeScreenshot(fileName)
        spockControl.canTakeScreenShot()

        then:
        1 * mockFluentControl.takeScreenshot()
        1 * mockFluentControl.takeScreenshot(fileName)
        1 * mockFluentControl.canTakeScreenShot()
    }

    def 'should call await'() {
        when:
        spockControl.await()

        then:
        1 * mockFluentControl.await()
    }

    def 'should call getChromiumApi'() {
        when:
        spockControl.getChromiumApi()

        then:
        1 * mockFluentControl.getChromiumApi()
    }
}