package io.fluentlenium.adapter.testng;

import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.configuration.DefaultConfigurationFactory;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.search.SearchFilter;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.DEFAULT;
import static io.fluentlenium.configuration.ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL;
import static org.mockito.Mockito.*;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class ControlUnitTest {

    @Mock
    private FluentControlContainer fluentControlContainer;

    @Mock
    private FluentControl fluentControl;

    @Mock
    private FluentList<FluentWebElement> fluentWebElements;

    @Mock
    private List<WebElement> webElements;

    @Mock
    private WebElement webElement;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private Configuration configuration;

    @Mock
    private SearchFilter searchFilter;

    @Mock
    private By by;

    @Mock
    private Iterable<WebElement> webElementsIterable;

    private SpringTestNGControl control;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(fluentControlContainer.getFluentControl()).thenReturn(fluentControl);
        control = new SpringTestNGControl(fluentControlContainer, configuration);
    }

    @AfterMethod
    public void cleanup() {
        Mockito.reset();
    }

    @Test
    public void shouldCallGetDriver() {
        control.getDriver();
        verify(fluentControl, times(1)).getDriver();
    }

    @Test
    public void shouldCallGetAppiumDriver() {
        control.getAppiumDriver();
        verify(fluentControl, times(1)).getAppiumDriver();
    }

    @Test
    public void shouldCallGetConfigurationDefaults() {
        control.getConfigurationDefaults();
        verify(configuration, times(1)).getConfigurationDefaults();
    }

    @Test
    public void shouldCallGetSetAwaitPollingEvery() {
        control.getAwaitPollingEvery();
        control.setAwaitPollingEvery(1L);
        verify(configuration, times(1)).getAwaitPollingEvery();
        verify(configuration, times(1)).setAwaitPollingEvery(1L);
    }

    @Test
    public void shouldCallGetSetAwaitAtMost() {
        control.getAwaitAtMost();
        control.setAwaitAtMost(1L);
        verify(configuration, times(1)).getAwaitAtMost();
        verify(configuration, times(1)).setAwaitAtMost(1L);
    }

    @Test
    public void shouldCallGetSetCustomProperty() {
        control.setCustomProperty("key", "value");
        control.getCustomProperty("key");
        verify(configuration, times(1)).setCustomProperty("key", "value");
        verify(configuration, times(1)).getCustomProperty("key");
    }

    @Test
    public void shouldCallGetSetBrowserTimeoutRetries() {
        control.setBrowserTimeoutRetries(1);
        control.getBrowserTimeoutRetries();
        verify(configuration, times(1)).setBrowserTimeoutRetries(1);
        verify(configuration, times(1)).getBrowserTimeoutRetries();
    }

    @Test
    public void shouldCallGetSetBrowserTimeout() {
        control.setBrowserTimeout(1L);
        control.getBrowserTimeout();
        verify(configuration, times(1)).setBrowserTimeout(1L);
        verify(configuration, times(1)).getBrowserTimeout();
    }

    @Test
    public void shouldCallGetSetWebDriver() {
        control.setWebDriver("chrome");
        control.getWebDriver();
        verify(configuration, times(1)).setWebDriver("chrome");
        verify(configuration, times(1)).getWebDriver();
    }

    @Test
    public void shouldCallGetSetDeleteCookies() {
        control.setDeleteCookies(true);
        control.getDeleteCookies();
        verify(configuration, times(1)).setDeleteCookies(true);
        verify(configuration, times(1)).getDeleteCookies();
    }

    @Test
    public void shouldCallGetSetScreenshotPath() {
        control.setScreenshotPath("/path");
        control.getScreenshotPath();
        verify(configuration, times(1)).setScreenshotPath("/path");
        verify(configuration, times(1)).getScreenshotPath();
    }

    @Test
    public void shouldCallGetSetScreenshotMode() {
        control.setScreenshotMode(AUTOMATIC_ON_FAIL);
        control.getScreenshotMode();
        verify(configuration, times(1)).setScreenshotMode(AUTOMATIC_ON_FAIL);
        verify(configuration, times(1)).getScreenshotMode();
    }

    @Test
    public void shouldCallGetSetHtmlDumpPath() {
        control.setHtmlDumpPath("/path");
        control.getHtmlDumpPath();
        verify(configuration, times(1)).setHtmlDumpPath("/path");
        verify(configuration, times(1)).getHtmlDumpPath();
    }

    @Test
    public void shouldCallGetSetHtmlDumpMode() {
        control.setHtmlDumpMode(AUTOMATIC_ON_FAIL);
        control.getHtmlDumpMode();
        verify(configuration, times(1)).setHtmlDumpMode(AUTOMATIC_ON_FAIL);
        verify(configuration, times(1)).getHtmlDumpMode();
    }

    @Test
    public void shouldCallGetSetBaseUrl() {
        control.setBaseUrl("url");
        control.getBaseUrl();
        verify(configuration, times(1)).setBaseUrl("url");
        verify(configuration, times(1)).getBaseUrl();
    }

    @Test
    public void shouldCallGetSetPageLoadTimeout() {
        control.setPageLoadTimeout(1L);
        control.getPageLoadTimeout();
        verify(configuration, times(1)).setPageLoadTimeout(1L);
        verify(configuration, times(1)).getPageLoadTimeout();
    }

    @Test
    public void shouldCallGetSetConfigurationFactory() {
        control.setConfigurationFactory(DefaultConfigurationFactory.class);
        control.getConfigurationFactory();
        verify(configuration, times(1)).setConfigurationFactory(DefaultConfigurationFactory.class);
        verify(configuration, times(1)).getConfigurationFactory();
    }

    @Test
    public void shouldCallGetSetDriverLifecycle() {
        control.setDriverLifecycle(DEFAULT);
        control.getDriverLifecycle();
        verify(configuration, times(1)).setDriverLifecycle(DEFAULT);
        verify(configuration, times(1)).getDriverLifecycle();
    }

    @Test
    public void shouldCallGetSetRemote() {
        control.setRemoteUrl("http://");
        control.getRemoteUrl();
        verify(configuration, times(1)).setRemoteUrl("http://");
        verify(configuration, times(1)).getRemoteUrl();
    }

    @Test
    public void shouldCallGetSetEventsEnabled() {
        control.setEventsEnabled(true);
        control.getEventsEnabled();
        verify(configuration, times(1)).setEventsEnabled(true);
        verify(configuration, times(1)).getEventsEnabled();
    }

    @Test
    public void shouldCallGetSetScriptTimeout() {
        control.setScriptTimeout(1L);
        control.getScriptTimeout();
        verify(configuration, times(1)).setScriptTimeout(1L);
        verify(configuration, times(1)).getScriptTimeout();
    }

    @Test
    public void shouldCallGetSetImplicitlyWait() {
        control.setImplicitlyWait(1L);
        control.getImplicitlyWait();
        verify(configuration, times(1)).setImplicitlyWait(1L);
        verify(configuration, times(1)).getImplicitlyWait();
    }

    @Test
    public void shouldCallGetSetCapabilities() {
        Capabilities capabilities = Mockito.mock(Capabilities.class);
        control.setCapabilities(capabilities);
        control.getCapabilities();
        verify(configuration, times(1)).setCapabilities(capabilities);
        verify(configuration, times(1)).getCapabilities();
    }

    @Test
    public void shouldCallScreenshotMethods() {
        control.canTakeScreenShot();
        control.takeScreenshot();
        control.takeScreenshot("/path");
        verify(fluentControl, times(1)).canTakeScreenShot();
        verify(fluentControl, times(1)).takeScreenshot();
        verify(fluentControl, times(1)).takeScreenshot("/path");
    }

    @Test
    public void shouldCallHtmlDumpMethods() {
        control.takeHtmlDump();
        control.takeHtmlDump("/path");
        verify(fluentControl, times(1)).takeHtmlDump();
        verify(fluentControl, times(1)).takeHtmlDump("/path");
    }

    @Test
    public void shouldCallGetChromiumApi() {
        control.getChromiumApi();
        verify(fluentControl, times(1)).getChromiumApi();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallSwitchMethods() {
        control.switchTo();
        control.switchToDefault();
        control.switchTo(fluentWebElements);
        control.switchTo(fluentWebElement);
        verify(fluentControl, times(1)).switchTo();
        verify(fluentControl, times(1)).switchToDefault();
        verify(fluentControl, times(1)).switchTo(fluentWebElements);
        verify(fluentControl, times(1)).switchTo(fluentWebElement);
    }

    @Test
    public void shouldCallGoToMethods() {
        FluentPage page = Mockito.mock(FluentPage.class);
        String url = "url";
        control.goTo(url);
        control.goTo(page);
        control.goToInNewTab(url);
        verify(fluentControl, times(1)).goTo(url);
        verify(fluentControl, times(1)).goToInNewTab(url);
        verify(fluentControl, times(1)).goTo(page);
    }

    @Test
    public void shouldCallExecuteScriptMethods() {
        String script = "";
        control.executeScript(script);
        control.executeAsyncScript(script);
        verify(fluentControl, times(1)).executeScript(script);
        verify(fluentControl, times(1)).executeAsyncScript(script);
    }

    @Test
    public void shouldCallCookieMethods() {
        control.getCookie("cookie");
        control.getCookies();
        verify(fluentControl, times(1)).getCookie("cookie");
        verify(fluentControl, times(1)).getCookies();
    }

    @Test
    public void shouldCallMouseKeyboardMethods() {
        control.mouse();
        control.keyboard();
        verify(fluentControl, times(1)).mouse();
        verify(fluentControl, times(1)).keyboard();
    }

    @Test
    public void shouldCallFindMethods() {
        control.find(searchFilter);
        control.find(by, searchFilter);
        control.find(webElements);
        control.find("", searchFilter);
        verify(fluentControl, times(1)).find(searchFilter);
        verify(fluentControl, times(1)).find(by, searchFilter);
        verify(fluentControl, times(1)).find(webElements);
        verify(fluentControl, times(1)).find("", searchFilter);
    }

    @Test
    public void shouldCallCapabilitiesMethod() {
        control.capabilities();
        verify(fluentControl, times(1)).capabilities();
    }

    @Test
    public void shouldCallElMethod() {
        control.el(webElement);
        verify(fluentControl, times(1)).el(webElement);
    }

    @Test
    public void shouldCallAsFluentListMethods() {
        control.asFluentList(webElement);
        control.asFluentList(webElements);
        control.asFluentList(webElementsIterable);
        control.asFluentList(FluentWebElement.class, webElementsIterable);
        control.asFluentList(FluentWebElement.class, webElement);
        control.asFluentList(FluentWebElement.class, webElements);

        verify(fluentControl, times(1)).asFluentList(webElement);
        verify(fluentControl, times(1)).asFluentList(webElements);
        verify(fluentControl, times(1)).asFluentList(webElementsIterable);
        verify(fluentControl, times(1)).asFluentList(FluentWebElement.class, webElementsIterable);
        verify(fluentControl, times(1)).asFluentList(FluentWebElement.class, webElements);
        verify(fluentControl, times(1)).asFluentList(FluentWebElement.class, webElement);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallAsComponentListMethods() {
        control.asComponentList(FluentWebElement.class, webElement);
        control.asComponentList(FluentWebElement.class, webElements);
        control.asComponentList(FluentWebElement.class, webElementsIterable);
        control.asComponentList(FluentList.class, FluentWebElement.class, webElement);
        control.asComponentList(FluentList.class, FluentWebElement.class, webElements);
        control.asComponentList(FluentList.class, FluentWebElement.class, webElementsIterable);

        verify(fluentControl, times(1)).asComponentList(FluentWebElement.class, webElement);
        verify(fluentControl, times(1)).asComponentList(FluentWebElement.class, webElements);
        verify(fluentControl, times(1)).asComponentList(FluentWebElement.class, webElementsIterable);
        verify(fluentControl, times(1)).asComponentList(FluentList.class, FluentWebElement.class, webElement);
        verify(fluentControl, times(1)).asComponentList(FluentList.class, FluentWebElement.class, webElements);
        verify(fluentControl, times(1)).asComponentList(FluentList.class, FluentWebElement.class, webElementsIterable);

    }

    @Test
    public void shouldCallNewFluentListMethods() {
        control.newFluentList(fluentWebElement);
        control.newFluentList(FluentWebElement.class, fluentWebElements);
        control.newFluentList(FluentWebElement.class, fluentWebElement);
        control.newFluentList();
        control.newFluentList(FluentWebElement.class);
        control.newFluentList(fluentWebElements);

        verify(fluentControl, times(1)).newFluentList(fluentWebElement);
        verify(fluentControl, times(1)).newFluentList(FluentWebElement.class, fluentWebElements);
        verify(fluentControl, times(1)).newFluentList(FluentWebElement.class, fluentWebElement);
        verify(fluentControl, times(1)).newFluentList();
        verify(fluentControl, times(1)).newFluentList(FluentWebElement.class);
        verify(fluentControl, times(1)).newFluentList(fluentWebElements);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallNewComponentListMethods() {
        control.newComponentList(FluentWebElement.class);
        control.newComponentList(FluentWebElement.class, fluentWebElement, fluentWebElement);
        control.newComponentList(FluentWebElement.class, fluentWebElements);
        control.newComponentList(FluentList.class, FluentWebElement.class);
        control.newComponentList(FluentList.class, FluentWebElement.class, fluentWebElement);
        control.newComponentList(FluentList.class, FluentWebElement.class, fluentWebElements);

        verify(fluentControl, times(1)).newComponentList(FluentWebElement.class);
        verify(fluentControl, times(1)).newComponentList(FluentWebElement.class, fluentWebElement, fluentWebElement);
        verify(fluentControl, times(1)).newComponentList(FluentWebElement.class, fluentWebElements);
        verify(fluentControl, times(1)).newComponentList(FluentList.class, FluentWebElement.class);
        verify(fluentControl, times(1)).newComponentList(FluentList.class, FluentWebElement.class, fluentWebElement);
        verify(fluentControl, times(1)).newComponentList(FluentList.class, FluentWebElement.class, fluentWebElements);
    }

    @Test
    public void shouldCallInjectComponent() {
        Object one = new Object();
        Object two = new Object();
        SearchContext searchContext = Mockito.mock(SearchContext.class);
        control.injectComponent(one, two, searchContext);
        verify(fluentControl, times(1)).injectComponent(one, two, searchContext);
    }

    @Test
    public void shouldCallInject() {
        Object one = new Object();
        control.inject(one);
        verify(fluentControl, times(1)).inject(one);
    }

    @Test
    public void shouldCallCss() {
        control.css();
        verify(fluentControl, times(1)).css();
    }

    @Test
    public void shouldCallWindow() {
        control.window();
        verify(fluentControl, times(1)).window();
    }

    @Test
    public void shouldCallNewFluent() {
        control.newFluent(webElement);
        verify(fluentControl, times(1)).newFluent(webElement);
    }

    @Test
    public void shouldCallAlert() {
        control.alert();
        verify(fluentControl, times(1)).alert();
    }

    @Test
    public void shouldCallAwait() {
        control.await();
        verify(fluentControl, times(1)).await();
    }

    @Test
    public void shouldCallIsComponentClass() {
        control.isComponentClass(FluentWebElement.class);
        verify(fluentControl, times(1)).isComponentClass(FluentWebElement.class);
    }

    @Test
    public void shouldCallNewInstance() {
        control.newInstance(FluentWebElement.class);
        verify(fluentControl, times(1)).newInstance(FluentWebElement.class);
    }

    @Test
    public void shouldCallUrl() {
        control.url();
        verify(fluentControl, times(1)).url();
    }

    @Test
    public void shouldCallPageSource() {
        control.pageSource();
        verify(fluentControl, times(1)).pageSource();
    }

    @Test
    public void shouldCallPerformanceTiming() {
        control.performanceTiming();
        verify(fluentControl, times(1)).performanceTiming();
    }

    @Test
    public void shouldCallEvents() {
        control.events();
        verify(fluentControl, times(1)).events();
    }

    @Test
    public void shouldNewComponent() {
        control.newComponent(FluentWebElement.class, webElement);
        verify(fluentControl, times(1)).newComponent(FluentWebElement.class, webElement);
    }
}
