package io.fluentlenium.it;

import io.fluentlenium.adapter.junit.FluentTest;
import io.fluentlenium.core.FluentControl;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class FluentIntegTest extends FluentTest {
    @Rule
    public TestName name = new TestName();

    String inputsHtmlLocation;

    @Before
    public void locateInputsHtml() {
        File file = new File("../junit-common/src/main/resources/inputs.html");

        assertTrue(file.exists());

        inputsHtmlLocation = file.toURI().toString();
    }

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
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
