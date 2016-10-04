package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperclassPageTest extends PageTest {

    @Page
    private AnotherPage anotherPage;
    @Page
    private AnotherPage page2;

    @Test
    public void checkGoToPagesDeclaredInThisClassAndSuperclass() {
        page.go();
        assertThat(window().title()).contains("Selenium");
        anotherPage.go();
        assertThat(window().title()).contains("Another Page");
    }

    @Test
    public void checkGoToPagesOverridingPageDeclaredInSuperclass() {
        page2.go();
        assertThat(window().title()).contains("Another Page");
    }
}

class AnotherPage extends FluentPage {

    @Override
    public String getUrl() {
        return IntegrationFluentTest.ANOTHERPAGE_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Another Page");
    }
}
