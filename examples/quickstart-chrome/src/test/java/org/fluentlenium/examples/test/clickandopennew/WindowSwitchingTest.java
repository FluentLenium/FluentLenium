package org.fluentlenium.examples.test.clickandopennew;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.clickandopennew.AwesomeTestingBlogPage;
import org.junit.Test;

public class WindowSwitchingTest extends FluentTest {

    @Page
    private AwesomeTestingBlogPage testingBlogPage;

    @Test
    public void shouldOpenNewWindow() {
        goTo(testingBlogPage).isAt();
        testingBlogPage.clickLinkAndSwitchWindow().isAt();
    }

}
