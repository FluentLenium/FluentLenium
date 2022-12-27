package io.fluentlenium.core.events;

import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.*;

/**
 * Listener interface for events.
 */
public interface EventListener {

    /**
     * Called before {@link org.openqa.selenium.WebDriver#get get(String url)},
     * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}.
     *
     * @param url    URL
     * @param driver WebDriver
     */
    void beforeNavigateTo(String url, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver#get get(String url)},
     * {@link org.openqa.selenium.WebDriver.Navigation#to navigate().to(String url)}. Not called, if
     * an exception is thrown.
     *
     * @param url    URL
     * @param driver WebDriver
     */
    void afterNavigateTo(String url, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#back navigate().back()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateBack(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation navigate().back()}. Not called,
     * if an
     * exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateBack(WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateForward(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward navigate().forward()}.
     * Not called,
     * if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateForward(WebDriver driver);

    /**
     * Called before {@link WebDriver#findElement WebDriver.findElement(...)},
     * {@link WebDriver#findElements WebDriver.findElements(...)}, {@link org.openqa.selenium.WebElement#findElement
     * WebElement.findElement(...)}, {@link org.openqa.selenium.WebElement
     * #findElement WebElement.findElements(...)}.
     *
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
     * @param by      locator being used
     * @param driver  WebDriver
     */
    void beforeFindBy(By by, FluentWebElement element, WebDriver driver);

    /**
     * Called after {@link WebDriver#findElement WebDriver.findElement(...)},
     * {@link WebDriver#findElements WebDriver.findElements(...)}, {@link org.openqa.selenium.WebElement#findElement
     * WebElement.findElement(...)}, {@link org.openqa.selenium.WebElement#findElement WebElement.findElements(...)}.
     *
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
     * @param by      locator being used
     * @param driver  WebDriver
     */
    void afterFindBy(By by, FluentWebElement element, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebElement#click WebElement.click()}.
     *
     * @param driver  WebDriver
     * @param element the WebElement being used for the action
     */
    void beforeClickOn(FluentWebElement element, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebElement#click WebElement.click()}. Not called, if an exception is
     * thrown.
     *
     * @param driver  WebDriver
     * @param element the WebElement being used for the action
     */
    void afterClickOn(FluentWebElement element, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
     * {@link org.openqa.selenium.WebElement#sendKeys},
     * {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)}.
     *
     * @param element      the WebElement being used for the action
     * @param driver       WebDriver
     * @param charSequence value of the element
     */
    void beforeChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence);

    /**
     * Called after {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
     * {@link org.openqa.selenium.WebElement#sendKeys},
     * {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)} . Not called, if an exception is thrown.
     *
     * @param element      the WebElement being used for the action
     * @param driver       WebDriver
     * @param charSequence value of the element
     */
    void afterChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence);

    /**
     * Called before
     * {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object[]) }
     *
     * @param driver WebDriver
     * @param script the script to be executed
     */
    void beforeScript(String script, WebDriver driver);

    /**
     * Called after
     * {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object[]) }
     * .
     * Not called if an exception is thrown
     *
     * @param driver WebDriver
     * @param script the script that was executed
     */
    void afterScript(String script, WebDriver driver);

    /**
     * Called whenever an exception would be thrown.
     *
     * @param driver    WebDriver
     * @param throwable the exception that will be thrown
     */
    void onException(Throwable throwable, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateRefresh(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}. Not called,
     * if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateRefresh(WebDriver driver);

    /**
     * Called  before {@link Alert#accept()}
     *
     * @param driver WebDriver
     */
    void beforeAlertAccept(WebDriver driver);

    /**
     * Called  after {@link Alert#accept()}
     *
     * @param driver WebDriver
     */
    void afterAlertAccept(WebDriver driver);

    /**
     * Called  before {@link Alert#dismiss()}
     *
     * @param driver WebDriver
     */
    void beforeAlertDismiss(WebDriver driver);

    /**
     * Called  after {@link Alert#dismiss()}
     *
     * @param driver WebDriver
     */
    void afterAlertDismiss(WebDriver driver);

    /**
     * Called  before {@link WebDriver.Window#switchTo()} ()}
     *
     * @param s      String
     * @param driver WebDriver
     */
    void beforeSwitchToWindow(String s, WebDriver driver);

    /**
     * Called  after {@link WebDriver.Window#switchTo()} ()}
     *
     * @param s      String
     * @param driver WebDriver
     */
    void afterSwitchToWindow(String s, WebDriver driver);

    /**
     * Called  before {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType)} ()}
     *
     * @param <X>        object
     * @param outputType OutputType
     */
    <X> void beforeGetScreenshotAs(OutputType<X> outputType);

    /**
     * Called  after {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType)} ()}
     *
     * @param <X>        object
     * @param outputType OutputType
     * @param x          object
     */
    <X> void afterGetScreenshotAs(OutputType<X> outputType, X x);

    /**
     * Called  before {@link WebElement#getText()} ()}
     *
     * @param webElement WebElement
     * @param webDriver  WebDriver
     */
    void beforeGetText(FluentWebElement webElement, WebDriver webDriver);

    /**
     * Called  after {@link WebElement#getText()} ()}
     *
     * @param webElement WebElement
     * @param webDriver  WebDriver
     * @param s          String
     */
    void afterGetText(FluentWebElement webElement, WebDriver webDriver, String s);
}
