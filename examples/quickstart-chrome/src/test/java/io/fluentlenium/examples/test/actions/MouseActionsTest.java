package io.fluentlenium.examples.test.actions;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import io.fluentlenium.examples.test.AbstractChromeTest;
import org.junit.Test;

public class MouseActionsTest extends AbstractChromeTest {

    private static final String SEARCH_PHRASE = "FluentLenium";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Test
    public void testFindByFluentWebElementActions() {
        goTo(duckDuckMainPage)
                .testFindByFluentWebElementActions(SEARCH_PHRASE)
                .assertIsPhrasePresentInTheResults(SEARCH_PHRASE);
    }

    @Test
    public void testFluentWebElementActions() {
        goTo(duckDuckMainPage)
                .testFluentWebElementActions(SEARCH_PHRASE)
                .assertIsPhrasePresentInTheResults(SEARCH_PHRASE);
    }


}
