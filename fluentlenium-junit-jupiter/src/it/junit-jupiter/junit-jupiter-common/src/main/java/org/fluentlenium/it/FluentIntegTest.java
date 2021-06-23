package org.fluentlenium.it;

import java.util.Date;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.FluentControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.launcher.TestExecutionListener;
import org.openqa.selenium.WebDriver;

public class FluentIntegTest extends FluentTest implements TestExecutionListener {

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
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
    public FluentControl initFluent(WebDriver webDriver) {
        return super.initFluent(webDriver);
    }

    @Override
    public boolean releaseFluent() {
        return super.releaseFluent();
    }
}
