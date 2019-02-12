package org.fluentlenium.example.spring;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.spring.page.MainPage;
import org.junit.Test;

public class GoogleSearchTest extends ExampleFluentTest {
    @Page
    private MainPage mainPage;

    @Test
    public void visitGoogle() {
        goTo(mainPage)
                .typeTextIn()
                .startSearch()
                .waitForResults();
    }
}
