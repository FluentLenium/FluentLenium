package org.fluentlenium.core.inject;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.remote.MobileCapabilityType;
import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InjectionAnnotationsTest {

    @FindBy(css = "css")
    private FluentWebElement css;

    @FindBy(xpath = "xpath")
    private FluentWebElement xpath;

    @iOSXCUITFindBy(accessibility = "iosAccessibilityId")
    private FluentWebElement iosAccessibilityId;

    @iOSXCUITFindBys({@iOSXCUITBy(id = "oneline"), @iOSXCUITBy(className = "small")})
    private FluentWebElement iosFindBys;

    @AndroidFindBy(uiAutomator = "androidUiAutomator")
    private FluentWebElement androidUiAutomator;

    @WindowsFindBy(windowsAutomation = "windowsAutomation")
    private FluentWebElement windowsAutomation;

    @Test
    public void shouldInjectCssField() throws NoSuchFieldException {
        Field cssField = this.getClass().getDeclaredField("css");
        InjectionAnnotations annotations = new InjectionAnnotations(cssField, null);
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(By.ByCssSelector.class);
        assertThat(by).isEqualTo(By.cssSelector("css"));
    }

    @Test
    public void shouldInjectXpathField() throws NoSuchFieldException {
        Field xpathField = this.getClass().getDeclaredField("xpath");
        InjectionAnnotations annotations = new InjectionAnnotations(xpathField, null);
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(By.ByXPath.class);
        assertThat(by).isEqualTo(By.xpath("xpath"));
    }

    @Test
    public void shouldInjectIosAccessibilityIdField() throws NoSuchFieldException {
        Field iosAccessibilityField = this.getClass().getDeclaredField("iosAccessibilityId");
        InjectionAnnotations annotations = new InjectionAnnotations(iosAccessibilityField, getIosCapablities());
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(ContentMappedBy.class);
        assertThat(by).isEqualTo(new ByChained(MobileBy.AccessibilityId("iosAccessibilityId")));
    }

    @Test
    public void shouldInjectIosFindAllField() throws NoSuchFieldException {
        Field iosFindAllField = this.getClass().getDeclaredField("iosFindBys");
        InjectionAnnotations annotations = new InjectionAnnotations(iosFindAllField, getIosCapablities());
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(ContentMappedBy.class);
        ByChained expectedBy = new ByChained(new ByChained(MobileBy.id("oneline"), MobileBy.className("small")));
        assertThat(by).isEqualTo(expectedBy);
    }

    @Test
    public void shouldInjectAndroidAccessibilityIdField() throws NoSuchFieldException {
        Field androidUiAutomator = this.getClass().getDeclaredField("androidUiAutomator");
        InjectionAnnotations annotations = new InjectionAnnotations(androidUiAutomator, getAndroidCapablities());
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(ContentMappedBy.class);
        assertThat(by).isEqualTo(new ByChained(MobileBy.AndroidUIAutomator("androidUiAutomator")));
    }

    @Test
    public void shouldInjectWindowsAutomationField() throws NoSuchFieldException {
        Field windowsAutomation = this.getClass().getDeclaredField("windowsAutomation");
        InjectionAnnotations annotations = new InjectionAnnotations(windowsAutomation, getWindowsCapabilities());
        By by = annotations.buildBy();
        assertThat(by).isInstanceOf(ContentMappedBy.class);
        assertThat(by).isEqualTo(new ByChained(MobileBy.ByWindowsAutomation.windowsAutomation("windowsAutomation")));
    }

    @Test
    public void shouldThrowExceptionWhenCapabilitiesAreIncomplete() throws NoSuchFieldException {
        Field androidUiAutomator = this.getClass().getDeclaredField("androidUiAutomator");
        assertThatThrownBy(() -> new InjectionAnnotations(androidUiAutomator, getIncompleteAndroidCapabilties()))
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("You have annotated elements with Appium @FindBys but capabilities are incomplete");
    }

    private Capabilities getIncompleteAndroidCapabilties() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        return capabilities;
    }

    private Capabilities getAndroidCapablities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        return capabilities;
    }

    private Capabilities getIosCapablities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        return capabilities;
    }

    private Capabilities getWindowsCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "Windows");
        return capabilities;
    }

}
