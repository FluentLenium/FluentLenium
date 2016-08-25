package org.fluentlenium.core.action;

import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.core.wait.FluentWaitWindowMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
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
    private WebDriver.TargetLocator targetLocator;

    @Before
    public void before() {
        when(fluentDriver.getDriver()).thenReturn(driver);
        when(driver.manage()).thenReturn(options);
        when(driver.manage().window()).thenReturn(window);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.switchTo().window(anyString())).thenReturn(driver);
    }

    @After
    public void after() {
        reset(driver, window, fluentDriver);
    }

    @Test
    public void getWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        windowAction.getWindow();
        verify(driver.manage(), times(1)).window();
    }

    @Test
    public void maximizeWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        windowAction.maximizeWindow();
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).maximize();
    }

    @Test
    public void fullScreenWindowTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        windowAction.fullScreen();
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).fullscreen();
    }

    @Test
    public void switchToTest() {
        String windowHandle = "WndH1";

        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        when(driver.getWindowHandle()).thenReturn(windowHandle);

        windowAction.switchTo(windowHandle);

        verify(driver, times(1)).manage();
        verify(driver, times(2)).switchTo();
    }

    @Test
    public void switchToLast() {
        String windowHandle = "WndH1";
        String windowHandle2 = "WndH2";

        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        when(driver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle2));

        windowAction.switchToLast();

        verify(driver, times(1)).manage();
        verify(driver, times(2)).switchTo();
    }

    @Test
    public void switchToParentFrame() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);

        windowAction.switchToParentFrame();

        verify(driver, times(1)).manage();
        verify(driver.switchTo(), times(1)).parentFrame();
    }

    @Test
    public void setSizeTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        Dimension dim = new Dimension(100, 200);
        windowAction.setSize(dim);
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).setSize(eq(dim));
    }

    @Test
    public void clickAndCloseCurrentTest() throws InterruptedException {
        String windowHandle = "WndH1";
        String windowHandle2 = "WndH2";

        FluentWebElement fluentWebElement = Mockito.mock(FluentWebElement.class);
        FluentWait fluentWait = Mockito.mock(FluentWait.class);
        FluentWaitWindowMatcher fluentWaitWindowMatcher = Mockito.mock(FluentWaitWindowMatcher.class);

        when(driver.getWindowHandles()).thenReturn(ImmutableSet.of(windowHandle, windowHandle2));
        when(fluentWaitWindowMatcher.isNotDisplayed()).thenReturn(true);
        when(fluentWebElement.click()).thenReturn(fluentWebElement);
        when(fluentWait.untilWindow(anyString())).thenReturn(fluentWaitWindowMatcher);
        when(fluentDriver.await()).thenReturn(fluentWait);

        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        windowAction.clickAndCloseCurrent(fluentWebElement);

        verify(driver, times(1)).manage();
        verify(driver, times(1)).getWindowHandle();

    }

    @Test
    public void getSizeTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        Point pos = new Point(101, 201);

        when(driver.manage().window().getPosition()).thenReturn(pos);

        Point getPos = windowAction.getPosition();

        verify(driver.manage(), times(2)).window();
        verify(driver.manage().window(), times(1)).getPosition();
        Assertions.assertThat(getPos).isEqualTo(pos);
    }

    @Test
    public void getPositionTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        Dimension dim = new Dimension(101, 201);

        when(driver.manage().window().getSize()).thenReturn(dim);

        Dimension getSizeDim = windowAction.getSize();

        verify(driver.manage(), times(2)).window();
        verify(driver.manage().window(), times(1)).getSize();
        Assertions.assertThat(getSizeDim).isEqualTo(dim);
    }

    @Test
    public void setPositionTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        Point pos = new Point(101, 201);

        windowAction.setPosition(pos);
        verify(driver.manage(), times(1)).window();
        verify(driver.manage().window(), times(1)).setPosition(eq(pos));
    }

    @Test
    public void titleTest() {
        String title = "title";
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        when(driver.getTitle()).thenReturn(title);

        Assertions.assertThat(windowAction.title()).isEqualTo(title);

        verify(driver.manage(), times(0)).window();
    }

    @Test
    public void closeTest() {
        WindowAction windowAction = new WindowAction(fluentDriver, driver);
        windowAction.close();

        verify(driver.manage(), times(0)).window();
        verify(driver, times(1)).close();
    }
}
