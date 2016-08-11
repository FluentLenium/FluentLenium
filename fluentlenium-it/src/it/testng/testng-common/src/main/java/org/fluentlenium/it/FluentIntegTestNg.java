package org.fluentlenium.it;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Date;

public class FluentIntegTestNg extends FluentTestNg {
    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }


    @BeforeMethod
    public void printBefore(Method method) {
        System.out.println("Starting test " + method.getDeclaringClass().getName() + "." + method.getName() +
                " [" + System.identityHashCode(this) + "]" + " @ " + new Date() +
                ". forkNumber=" + System.getProperty("surefire.forkNumber") +
                ", thread=" + Thread.currentThread().getName());
    }

    @AfterMethod
    public void printAfter(Method method) {
        System.out.println("Terminating test " + method.getDeclaringClass().getName() + "." + method.getName() +
                " [" + System.identityHashCode(this) + "]" + " @ " + new Date() +
                ". forkNumber=" + System.getProperty("surefire.forkNumber") +
                ", thread=" + Thread.currentThread().getName());
    }

    @Override
    public FluentAdapter initFluent(WebDriver webDriver) {
        System.out.println("Init WebDriver " + webDriver + " for test " + getClass().getName() +
                " [" + System.identityHashCode(this) + "]");
        return super.initFluent(webDriver);
    }

    @Override
    public FluentAdapter releaseFluent() {
        System.out.println("Release WebDriver " + getDriver() + " for test " + getClass().getName() +
                " [" + System.identityHashCode(this) + "]");
        return super.releaseFluent();
    }
}
