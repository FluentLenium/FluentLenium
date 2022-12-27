package org.fluentlenium.example.spring.web;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.spring.page.JavadocPage;
import org.fluentlenium.example.spring.page.MainPage;
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
