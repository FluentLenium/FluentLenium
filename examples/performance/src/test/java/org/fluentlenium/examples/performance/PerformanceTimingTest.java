package org.fluentlenium.examples.performance;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.performance.PerformanceTimingMetrics;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PerformanceTimingTest extends FluentTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {
        return new ChromeDriver();
    }

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        DuckDuckMainPage duckDuckMainPage = goTo(this.duckDuckMainPage);

        //Retrieve a single metric in the default epoch value
        long loadEventStart = performanceTiming().loadEventStart();
        System.out.println(loadEventStart);

        //Retrieve a single metric converted to seconds
        long loadEventStartInSecs = performanceTiming().loadEventStart(TimeUnit.SECONDS);
        System.out.println(loadEventStartInSecs);

        duckDuckMainPage
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);

        /*Wait for the load completely so that all performance metrics values are registered on the page.
         * This may be any kind of wait that ensures that the page has loaded completely.
         * This is only necessary when the navigation happens after some interaction on the page.*/
        await().explicitlyFor(3, TimeUnit.SECONDS);

        //Retrieve all metrics in a single object
        PerformanceTimingMetrics metrics = performanceTiming().getMetrics();

        //Retrieve a single metric in the default epoch value
        long loadEventEnd = metrics.getLoadEventEnd();
        System.out.println(loadEventEnd);

        //Retrieve a metric converted to seconds
        long loadEventEndInSecs = metrics.in(TimeUnit.SECONDS).getLoadEventEnd();
        System.out.println(loadEventEndInSecs);
    }
}
