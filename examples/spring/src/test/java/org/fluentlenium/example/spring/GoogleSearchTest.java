package org.fluentlenium.example.spring;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.spring.page.MainPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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
