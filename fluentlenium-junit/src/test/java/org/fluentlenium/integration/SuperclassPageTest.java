package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperclassPageTest extends PageTest {

    @Page
    AnotherPage anotherPage;
    @Page
    AnotherPage page2;

    @Test
    public void checkGoToPagesDeclaredInThisClassAndSuperclass() {
        page.go();
        assertThat(title()).contains("Selenium");
        anotherPage.go();
        assertThat(title()).contains("Another Page");
    }

    @Test
    public void checkGoToPagesOverridingPageDeclaredInSuperclass() {
        page2.go();
        assertThat(title()).contains("Another Page");
    }
}

class AnotherPage extends FluentPage {

    @Override
    public String getUrl() {
        return LocalFluentCase.ANOTHERPAGE_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("Another Page");
    }
}
