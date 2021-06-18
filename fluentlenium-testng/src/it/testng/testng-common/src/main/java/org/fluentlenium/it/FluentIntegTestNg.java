package org.fluentlenium.it;

import org.fluentlenium.adapter.testng.FluentTestNg;
import org.fluentlenium.core.FluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Date;

public class FluentIntegTestNg extends FluentTestNg {
    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @BeforeMethod
    public void printBefore(Method method) {
        System.out.println("Starting test " + method.getDeclaringClass().getName() + "." + method.getName() + " [" + System
                .identityHashCode(this) + "]" + " @ " + new Date() + ". forkNumber=" + System.getProperty("surefire.forkNumber")
                + ", thread=" + Thread.currentThread().getName());
    }

    @AfterMethod
    public void printAfter(Method method) {
        System.out.println("Terminating test " + method.getDeclaringClass().getName() + "." + method.getName() + " [" + System
                .identityHashCode(this) + "]" + " @ " + new Date() + ". forkNumber=" + System.getProperty("surefire.forkNumber")
                + ", thread=" + Thread.currentThread().getName());
    }

    @Override
    public FluentControl initFluent(WebDriver webDriver) {
        System.out.println(
                "Init WebDriver " + webDriver + " for test " + getClass().getName() + " [" + System.identityHashCode(this) + "]");
        return super.initFluent(webDriver);
    }

    @Override
    public boolean releaseFluent() {
        System.out.println(
                "Release WebDriver " + getDriver() + " for test " + getClass().getName() + " [" + System.identityHashCode(this)
                        + "]");
        return super.releaseFluent();
    }
}
