package io.fluentlenium.example.spring.web;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.example.spring.page.JavadocPage;
import io.fluentlenium.example.spring.page.MainPage;
import org.testng.annotations.Test;

public class FluentleniumComTest extends ExampleFluentTest {

    @Page
    private MainPage mainPage;

    @Page
    private JavadocPage javadocPage;

    @Test
    public void visitFluentleniumCom() {
        goTo(mainPage)
                .verifyIfIsLoaded()
                .clickOnSeleniumLink();
    }

    @Test
    public void visitFluentleniumJavadoc() {
        goTo(javadocPage)
                .verifyIfIsLoaded();
    }
}
