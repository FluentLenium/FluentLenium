package org.fluentlenium.test.initialization;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

class DontRunTestsWhenInitFailTest {

    public static class IgnoreTestClass extends FluentTest {

        IgnoreTestClass() {
            getConfiguration().setScreenshotMode(TriggerMode.AUTOMATIC_ON_FAIL);
            getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        }

        @Override
        public WebDriver newWebDriver() {
            throw new RuntimeException("Error");
        }

        @Test
        void testDriverFailShouldNotCallTestMethod() {
            fail("Should not be called");
        }
    }

    @Test
    void testRun() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(IgnoreTestClass.class))
                .build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        assertThat(getNumberOfFailures(listener)).isEqualTo(1);
        assertThat(getFailureMessage(listener, 0)).contains("Error");
    }

    private int getNumberOfFailures(SummaryGeneratingListener listener) {
        return listener.getSummary().getFailures().size();
    }

    private String getFailureMessage(SummaryGeneratingListener listener, int index) {
        return listener.getSummary().getFailures().get(index).getException().getMessage();
    }

}
