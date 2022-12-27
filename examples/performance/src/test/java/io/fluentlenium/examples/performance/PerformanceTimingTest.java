package io.fluentlenium.examples.performance;

import org.assertj.core.api.SoftAssertions;
import io.fluentlenium.adapter.junit.FluentTest;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.performance.PerformanceTimingEvent;
import io.fluentlenium.core.performance.PerformanceTimingMetrics;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PerformanceTimingTest extends FluentTest {

    private static final String PERFORMANCE_TIMING_EVENTS_SCRIPT = "return window.performance.timing.%s;";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {
        return new ChromeDriver();
    }

    @Test
    public void demonstrateRelativePerformanceTimingValues() {
        goTo(duckDuckMainPage);

        //Navigation start will always be 0 because all other metrics are calculated relatively to this
        long navigationStart = performanceTiming().navigationStart();

        //Retrieve a single metric value containing the time passed in milliseconds since the moment of navigationStart
        long loadEventStart = performanceTiming().loadEventStart();

        //Same as the previous query only that it is converted to seconds
        long loadEventStartInSecs = performanceTiming().loadEventStart(SECONDS);

        //Retrieve a single metric value by parameter containing the time passed since the moment of navigationStart
        long domComplete = performanceTiming().getEventValue(PerformanceTimingEvent.DOM_COMPLETE);

        //Same as the previous query only that it is converted to seconds
        long domCompleteInSecs = performanceTiming().getEventValue(PerformanceTimingEvent.DOM_COMPLETE, SECONDS);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(navigationStart).isZero();
        softly.assertThat(loadEventStart).isEqualTo(timePassedSinceNavigationStart("loadEventStart"));
        softly.assertThat(loadEventStartInSecs).isEqualTo(convertToSeconds(loadEventStart));
        softly.assertThat(domComplete).isEqualTo(timePassedSinceNavigationStart("domComplete"));
        softly.assertThat(domCompleteInSecs).isEqualTo(convertToSeconds(domComplete));
        softly.assertAll();
    }

    @Test
    public void demonstrateBulkPerformanceTimingMetrics() {
        String searchPhrase = "searchPhrase";

        goTo(this.duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);

        /* Wait for the load completely so that all performance metrics values are registered on the page.
         * This may be any kind of wait that ensures that the page has loaded completely.
         * This is only necessary when the navigation happens after some interaction on the page.*/
        await().explicitlyFor(3, SECONDS);

        //Retrieve all metrics in a single object
        PerformanceTimingMetrics metrics = duckDuckMainPage.performanceTiming().getMetrics();

        //Navigation start will always be 0 because all other metrics are calculated relatively to this
        long navigationStart = metrics.getNavigationStart();

        //Retrieve a single metric containing the time passed in milliseconds since the moment of navigationStart
        long loadEventStart = metrics.getLoadEventStart();

        //Same as the previous query only that it is converted to seconds
        long loadEventStartInSecs = metrics.in(SECONDS).getLoadEventStart();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(navigationStart).isZero();
        softly.assertThat(loadEventStart).isEqualTo(timePassedSinceNavigationStart("loadEventStart"));
        softly.assertThat(loadEventStartInSecs).isEqualTo(convertToSeconds(loadEventStart));
        softly.assertAll();
    }

    private long timePassedSinceNavigationStart(String event) {
        return epochValueOf(event) - epochValueOf("navigationStart");
    }

    private long epochValueOf(String event) {
        return executeScript(String.format(PERFORMANCE_TIMING_EVENTS_SCRIPT, event)).getLongResult();
    }

    private long convertToSeconds(long value) {
        return SECONDS.convert(value, MILLISECONDS);
    }
}
