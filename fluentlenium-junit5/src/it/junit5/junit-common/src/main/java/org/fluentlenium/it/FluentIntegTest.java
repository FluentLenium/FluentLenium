package org.fluentlenium.it;

import org.fluentlenium.adapter.junit5.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Date;

public class FluentIntegTest extends FluentTest {
    @Rule
    public TestName name = new TestName();

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @Before
    public void printBefore() {
        System.out.println(
                "Starting test " + getClass().getName() + "." + name.getMethodName() + " [" + System.identityHashCode(this) + "]"
                        + " @ " + new Date() + ". forkNumber=" + System.getProperty("surefire.forkNumber") + ", thread=" + Thread
                        .currentThread().getName());
    }

    @After
    public void printAfter() {
        System.out.println(
                "Terminating test " + getClass().getName() + "." + name.getMethodName() + " [" + System.identityHashCode(this)
                        + "]" + " @ " + new Date() + ". forkNumber=" + System.getProperty("surefire.forkNumber") + ", thread="
                        + Thread.currentThread().getName());
    }

    @Override
    public void initFluent(WebDriver webDriver) {
        System.out.println(
                "Init WebDriver " + webDriver + " for test " + getClass().getName() + "." + name.getMethodName() + " [" + System
                        .identityHashCode(this) + "]");
        super.initFluent(webDriver);
    }

    @Override
    public void releaseFluent() {
        System.out.println(
                "Release WebDriver " + getDriver() + " for test " + getClass().getName() + "." + name.getMethodName() + " ["
                        + System.identityHashCode(this) + "]");
        super.releaseFluent();
    }
}
