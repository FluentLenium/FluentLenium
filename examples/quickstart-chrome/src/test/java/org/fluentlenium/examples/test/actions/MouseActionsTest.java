package org.fluentlenium.examples.test.actions;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.fluentlenium.examples.test.AbstractChromeTest;
import org.junit.Test;

public class MouseActionsTest extends AbstractChromeTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Test
    public void move() {
        goTo(duckDuckMainPage).testActions();
    }

}
