package org.fluentlenium.test.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SuperclassPageTest extends PageTest {

    @Page
    private AnotherPage anotherPage;
    @Page
    private AnotherPage page2;

    @Test
    void checkGoToPagesDeclaredInThisClassAndSuperclass() {
        page.go();
        assertThat(window().title()).contains("Selenium");
        anotherPage.go();
        assertThat(window().title()).contains("Another Page");
    }

    @Test
    void checkGoToPagesOverridingPageDeclaredInSuperclass() {
        page2.go();
        assertThat(window().title()).contains("Another Page");
    }
}

class AnotherPage extends FluentPage {

    @Override
    public String getUrl() {
        return IntegrationFluentTest.ANOTHER_PAGE_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Another Page");
    }
}
