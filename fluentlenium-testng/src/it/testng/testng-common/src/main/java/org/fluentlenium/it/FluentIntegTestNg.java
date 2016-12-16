package org.fluentlenium.it;

import org.fluentlenium.adapter.testng.FluentTestNg;
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
    public void initFluent(WebDriver webDriver) {
        System.out.println(
                "Init WebDriver " + webDriver + " for test " + getClass().getName() + " [" + System.identityHashCode(this) + "]");
        super.initFluent(webDriver);
    }

    @Override
    public void releaseFluent() {
        System.out.println(
                "Release WebDriver " + getDriver() + " for test " + getClass().getName() + " [" + System.identityHashCode(this)
                        + "]");
        super.releaseFluent();
    }
}
