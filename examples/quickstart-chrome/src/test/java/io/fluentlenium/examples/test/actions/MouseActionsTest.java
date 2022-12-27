package org.fluentlenium.examples.test.actions;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.fluentlenium.examples.test.AbstractChromeTest;
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
