package org.fluentlenium.test.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageWithAjaxElementTest extends IntegrationFluentTest {
    private static final int TIMEOUT_IN_MILLIS = 500;

    @Page
    private JavascriptPage page;

    @Page
    private JavascriptPageSlow pageSlow;

    @Page
    private JavascriptPageTooSlow pageTooSlow;

    @Page
    private JavascriptPageWithoutAjax pageWithoutAjax;

    @Override
    public FluentWait await() {
        return super.await().atMost(TIMEOUT_IN_MILLIS);
    }

    @Test
    void whenAjaxFieldsAreConsideredAsAjaxFieldsThenWaitForThem() {
        page.go();
        assertThat(page.getText()).isEqualTo("new");
    }

    @Test
    void whenAjaxFieldsAreFasterThanTimeoutThenWaitForThem() {
        pageSlow.go();
        assertThat(pageSlow.getText()).isEqualTo("new");
    }

    @Test
    void whenAjaxFieldsAreSlowerThanTimeoutThenNoSuchElementExceptionIsThrown() {
        assertThrows(TimeoutException.class,
                () -> {
                    pageTooSlow.go();
                    assertThat(pageTooSlow.getText()).isEqualTo("new");
                });
    }

    @Test
    void whenAjaxFieldsAreConsideredAsNormalFieldsThenNoSuchElementExceptionIsThrown() {
        assertThrows(NoSuchElementException.class,
                () -> {
                    pageWithoutAjax.go();
                    assertThat(pageWithoutAjax.getText()).isEqualTo("new");
                });
    }

    private static class JavascriptPage extends FluentPage {

        @Wait(timeout = 5000)
        private FluentWebElement newField;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }

        public String getText() {
            return newField.text();
        }
    }

    private static class JavascriptPageSlow extends FluentPage {

        @Wait(timeout = 12000)
        private FluentWebElement newFieldSlow;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }

        public String getText() {
            return newFieldSlow.text();
        }
    }

    private static class JavascriptPageTooSlow extends FluentPage {

        @Wait(timeout = 5000)
        private FluentWebElement newFieldSlow;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }

        public String getText() {
            return newFieldSlow.text();
        }
    }

    private static class JavascriptPageWithoutAjax extends FluentPage {

        private FluentWebElement newField;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }

        public String getText() {

            newField.click();

            return newField.text();
        }
    }
}

