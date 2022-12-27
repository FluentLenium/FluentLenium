package io.fluentlenium.adapter;

import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.utils.ExceptionUtil;
import io.fluentlenium.adapter.SharedMutator.EffectiveParameters;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.core.FluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.METHOD;
import static io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.THREAD;
import static io.fluentlenium.configuration.ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL;
import static io.fluentlenium.utils.ExceptionUtil.getCauseMessage;

public final class TestRunnerCommon {

    private TestRunnerCommon() {
    }

    public static void quitMethodAndThreadDrivers(
            DriverLifecycle driverLifecycle, SharedWebDriver sharedWebDriver) {

        if (driverLifecycle == METHOD || driverLifecycle == THREAD) {
            Optional.ofNullable(sharedWebDriver).ifPresent(SharedWebDriverContainer.INSTANCE::quit);
        }
    }

    public static void deleteCookies(SharedWebDriver sharedWebDriver, Configuration configuration) {
        if (configuration.getDeleteCookies()) {
            Optional.ofNullable(sharedWebDriver).ifPresent(shared -> shared.getDriver().manage().deleteAllCookies());
        }
    }

    public static SharedWebDriver getTestDriver(Class<?> testClass, String testName, Supplier<WebDriver> webDriver,
                                                TriConsumer<Throwable, Class<?>, String> failed,
                                                Configuration configuration, SharedMutator.EffectiveParameters<?> parameters) {
        SharedWebDriver sharedWebDriver;
        try {
            sharedWebDriver = SharedWebDriverContainer.INSTANCE.getSharedWebDriver(
                    parameters, null, webDriver, configuration);
        } catch (ExecutionException | InterruptedException e) {
            failed.accept(null, testClass, testName);

            String causeMessage = ExceptionUtil.getCauseMessage(e);
            throw new WebDriverException(String.format(
                    "Browser failed to start, test [ %s ] execution interrupted.%s",
                            testName, printCauseMessage(causeMessage)), e);
        }
        return sharedWebDriver;
    }

    private static String printCauseMessage(String causeMessage) {
        if (isEmpty(causeMessage)) {
            return "";
        }
        return String.format("\nCaused by: [ %s]", causeMessage);
    }

    public static void doHtmlDump(Class<?> testClass, String testName,
                                  FluentControl fluentControl, Configuration configuration) {
        try {
            if (configuration.getHtmlDumpMode() == AUTOMATIC_ON_FAIL
                    && fluentControl.getDriver() != null) {
                fluentControl.takeHtmlDump(String.format("%s_%s.html", testClass.getSimpleName(), testName));
            }
        } catch (Exception ignored) {
        }
    }

    public static void doScreenshot(Class<?> testClass, String testName,
                                    FluentControl fluentControl, Configuration configuration) {
        try {
            if (configuration.getScreenshotMode() == AUTOMATIC_ON_FAIL
                    && fluentControl.canTakeScreenShot()) {
                fluentControl.takeScreenshot(String.format("%s_%s.png", testClass.getSimpleName(), testName));
            }
        } catch (Exception ignored) {
        }
    }
}
