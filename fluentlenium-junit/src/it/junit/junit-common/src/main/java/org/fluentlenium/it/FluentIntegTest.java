package org.fluentlenium.it;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.FluentControl;
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
    public FluentControl initFluent(WebDriver webDriver) {
        System.out.println(
                "Init WebDriver " + webDriver + " for test " + getClass().getName() + "." + name.getMethodName() + " [" + System
                        .identityHashCode(this) + "]");
        return super.initFluent(webDriver);
    }

    @Override
    public boolean releaseFluent() {
        System.out.println(
                "Release WebDriver " + getDriver() + " for test " + getClass().getName() + "." + name.getMethodName() + " ["
                        + System.identityHashCode(this) + "]");
        return super.releaseFluent();
    }
}
