package io.fluentlenium.examples.test.clickandopennew;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.clickandopennew.AwesomeTestingBlogPage;
import io.fluentlenium.examples.test.AbstractChromeTest;
import org.junit.Test;

public class WindowSwitchingTest extends AbstractChromeTest {

    @Page
    private AwesomeTestingBlogPage testingBlogPage;

    @Test
    public void shouldOpenNewWindow() {
        goTo(testingBlogPage).isAt();
        testingBlogPage.clickLinkAndSwitchWindow().isAt();
    }

}
