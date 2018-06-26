package org.fluentlenium.it;

import java.util.Date;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.launcher.TestExecutionListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FluentIntegTest extends FluentTest implements TestExecutionListener {
    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }

    @BeforeEach
    public void printBefore(TestInfo name) {
        System.out.println(
                "Starting test " + getClass().getName() + "." + name.getTestMethod() + " [" + System
                        .identityHashCode(this) + "]"
                        + " @ " + new Date() + ". forkNumber=" + System.getProperty("surefire.forkNumber")
                        + ", thread=" + Thread.currentThread().getName());
    }

    @AfterEach
    public void printAfter(TestInfo name) {
        System.out.println("Terminating test " + getClass().getName() + "." + name.getTestMethod() +
                " [" + System.identityHashCode(this) + "]" + " @ " + new Date() + ". forkNumber=" +
                System.getProperty("surefire.forkNumber") + ", thread=" + Thread.currentThread().getName());
    }

    @Override
    public void initFluent(WebDriver webDriver) {
        super.initFluent(webDriver);
    }

    @Override
    public void releaseFluent() {
        super.releaseFluent();
    }
}
