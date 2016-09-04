package org.fluentlenium.core.domain;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.components.ComponentException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

public class FailWebElementTest {
    @Test
    public void testFailWebElement() {
        final FailWebElement failWebElement = new FailWebElement();

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.click();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.submit();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.sendKeys("abc");
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.clear();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getTagName();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getAttribute("abc");
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.isSelected();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.isEnabled();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getText();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.findElements(By.cssSelector("css"));
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.findElement(By.cssSelector("css"));
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.isDisplayed();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getLocation();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getSize();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getRect();
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getCssValue("css");
            }
        }).isExactlyInstanceOf(ComponentException.class);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                failWebElement.getScreenshotAs(OutputType.FILE);
            }
        }).isExactlyInstanceOf(ComponentException.class);
    }
}
