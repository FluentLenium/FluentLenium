package org.fluentlenium.it;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.WrapsDriver;

import java.util.Date;

public class FluentIntegTest extends FluentTest {
    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }

    @Rule
    public TestName name = new TestName();

    @Before
    public void printBefore() {
        System.out.println("Starting test " + getClass().getName() + "." + name.getMethodName() +
                " [" + System.identityHashCode(this) + "]" + " @ " + new Date() +
                ". forkNumber=" + System.getProperty("surefire.forkNumber") +
                ", thread=" + Thread.currentThread().getName());
    }

    @After
    public void printAfter() {
        System.out.println("Terminating test " + getClass().getName() + "." + name.getMethodName() +
                " [" + System.identityHashCode(this) + "]" + " @ " + new Date() +
                ". forkNumber=" + System.getProperty("surefire.forkNumber") +
                ", thread=" + Thread.currentThread().getName());
    }

    @Override
    public FluentAdapter initFluent(WebDriver webDriver) {
        System.out.println("Init WebDriver " + webDriver + " for test " + getClass().getName() +
                "." + name.getMethodName() + " [" + System.identityHashCode(this) + "]");
        return super.initFluent(webDriver);
    }

    @Override
    public FluentAdapter releaseFluent() {
        System.out.println("Release WebDriver " + getDriver() + " for test " + getClass().getName() +
                "." + name.getMethodName() + " [" + System.identityHashCode(this) + "]");
        return super.releaseFluent();
    }
}
