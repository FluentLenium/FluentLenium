package org.fluentlenium.core.action;

import com.google.common.collect.ImmutableSet;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.switchto.FluentTargetLocator;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.core.wait.FluentWaitWindowConditions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WindowActionsTest {
    @Mock
    private WebDriver driver;
    @Mock
    private WebDriver.Window window;
    @Mock
    private WebDriver.Options options;
    @Mock
    private FluentDriver fluentDriver;
    @Mock
    private FluentControl fluentControl;
    @Mock
    private ComponentInstantiator instantiator;
    @Mock
    private WebDriver.TargetLocator targetLocator;

    @Before
    public void before() {
        when(fluentDriver.getDriver()).thenReturn(driver);
        when(driver.manage()).thenReturn(options);
        when(driver.manage().window()).thenReturn(window);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.switchTo().window(any())).thenReturn(driver);
    }

    @After
    public void after() {
        reset(driver, window, fluentDriver);
    }

    @Test
    public void getWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        windowAction.getWindow();
        verify(driver.manage(), times(1)).window();
    }

    @Test
    public void maximizeWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        windowAction.maximize();
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).maximize();
    }

    @Test
    public void fullScreenWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        windowAction.fullscreen();
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).fullscreen();
    }

    @Test
    public void switchToTargetLocatorTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        FluentTargetLocator<WindowAction> switchTargetLocator = windowAction.switchTo();
        assertThat(switchTargetLocator).isNotNull();

        switchTargetLocator.parentFrame();
    }

    @Test
    public void switchToTest() {
        String windowHandle = "WndH1";

        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        when(driver.getWindowHandle()).thenReturn(windowHandle);

        windowAction.switchTo(windowHandle);

        verify(driver, times(1)).manage();
        verify(driver, times(2)).switchTo();
    }

    @Test
    public void switchToLast() {
        String windowHandle = "WndH1";
        String windowHandle2 = "WndH2";

        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        when(driver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle2));

        windowAction.switchToLast();

        verify(driver, times(1)).manage();
        verify(driver, times(2)).switchTo();
    }

    @Test
    public void openNewAndSwitch() {
        JavascriptWebDriver jsDriver = mock(JavascriptWebDriver.class);

        when(fluentDriver.getDriver()).thenReturn(jsDriver);
        when(jsDriver.switchTo()).thenReturn(targetLocator);
        when(jsDriver.switchTo().window(any())).thenReturn(driver);
        when(driver.getCurrentUrl()).thenReturn("https://fluentlenium.com");

        String windowHandle = "WndH1";
        String windowHandle1 = "WndH2";
        String windowHandle2 = "WndH3";

        Configuration configuration = mock(Configuration.class);

        FluentDriver currentFluentDriver = new FluentDriver(driver, configuration, fluentControl);
        FluentDriver fluentDriverSpied = spy(currentFluentDriver);

        when(jsDriver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle1),
                ImmutableSet.of(windowHandle, windowHandle1, windowHandle2));
        when(jsDriver.getWindowHandle()).thenReturn(windowHandle1, windowHandle2);

        WindowAction windowAction = new WindowAction(fluentDriverSpied, instantiator, jsDriver);
        windowAction.openNewAndSwitch();

        verify(jsDriver, times(1)).getWindowHandle();
        verify(jsDriver, times(3)).getWindowHandles();
        verify(jsDriver, times(2)).switchTo();
    }

    @Test
    public void switchToParentFrame() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);

        windowAction.switchTo().parentFrame();

        verify(driver, times(1)).manage();
        verify(driver.switchTo(), times(1)).parentFrame();
    }

    @Test
    public void setSizeTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        Dimension dim = new Dimension(100, 200);
        windowAction.setSize(dim);
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).setSize(eq(dim));
    }

    @Test
    public void clickAndCloseCurrentTest() {
        String windowHandle = "WndH1";
        String windowHandle2 = "WndH2";

        FluentWebElement fluentWebElement = mock(FluentWebElement.class);
        FluentWait fluentWait = mock(FluentWait.class);
        FluentWaitWindowConditions fluentWaitWindowMatcher = mock(FluentWaitWindowConditions.class);

        when(driver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle2));
        when(fluentWaitWindowMatcher.notDisplayed()).thenReturn(true);
        when(fluentWebElement.click()).thenReturn(fluentWebElement);
        when(fluentWait.untilWindow(any())).thenReturn(fluentWaitWindowMatcher);
        when(fluentDriver.await()).thenReturn(fluentWait);

        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        windowAction.clickAndCloseCurrent(fluentWebElement);

        verify(driver, times(1)).manage();
        verify(driver, times(1)).getWindowHandle();
    }

    @Test
    public void clickAndOpenNewTest() {
        String windowHandle = "WndH1";
        String windowHandle1 = "WndH2";
        String windowHandle2 = "WndH3";

        FluentWebElement fluentWebElement = mock(FluentWebElement.class);
        Configuration configuration = mock(Configuration.class);

        FluentDriver currentFluentDriver = new FluentDriver(driver, configuration, fluentControl);
        FluentDriver fluentDriverSpy = spy(currentFluentDriver);

        when(driver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle1),
                ImmutableSet.of(windowHandle, windowHandle1, windowHandle2));
        when(driver.getWindowHandle()).thenReturn(windowHandle1, windowHandle2);

        when(fluentWebElement.click()).thenReturn(fluentWebElement);
        when(driver.getCurrentUrl()).thenReturn("https://fluentlenium.com");

        WindowAction windowAction = new WindowAction(fluentDriverSpy, instantiator, driver);
        windowAction.clickAndOpenNew(fluentWebElement);

        verify(driver, times(3)).manage();
        verify(driver, times(3)).getWindowHandles();
    }

    @Test
    public void getSizeTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        Point pos = new Point(101, 201);

        when(driver.manage().window().getPosition()).thenReturn(pos);

        Point getPos = windowAction.getPosition();

        verify(driver.manage(), times(2)).window();
        verify(driver.manage().window(), times(1)).getPosition();
        assertThat(getPos).isEqualTo(pos);
    }

    @Test
    public void getPositionTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        Dimension dim = new Dimension(101, 201);

        when(driver.manage().window().getSize()).thenReturn(dim);

        Dimension getSizeDim = windowAction.getSize();

        verify(driver.manage(), times(2)).window();
        verify(driver.manage().window(), times(1)).getSize();
        assertThat(getSizeDim).isEqualTo(dim);
    }

    @Test
    public void setPositionTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        Point pos = new Point(101, 201);

        windowAction.setPosition(pos);
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).setPosition(eq(pos));
    }

    @Test
    public void titleTest() {
        String title = "title";
        WindowAction windowAction = new WindowAction(fluentDriver, instantiator, driver);
        when(driver.getTitle()).thenReturn(title);

        assertThat(windowAction.title()).isEqualTo(title);

        verify(driver.manage(), times(0)).window();
    }

    public interface JavascriptWebDriver extends WebDriver, JavascriptExecutor {

    }
}
