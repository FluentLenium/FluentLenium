package org.fluentlenium.integration;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

public class PageWithAjaxElementTest extends LocalFluentCase {
    @Page
    JavascriptPage page;

    @Page
    JavascriptPageSlow pageSlow;

    @Page
    JavascriptPageTooSlow pageTooSlow;

    @Page
    JavascriptPageWithoutAjax pageWithoutAjax;

    @Test
    public void when_ajax_fields_are_considered_as_ajax_fields_then_wait_for_them() {
        page.go();
        assertThat(page.getText()).isEqualTo("new");
    }

    @Test
    public void when_ajax_fields_are_faster_than_timeout_then_wait_for_them() {
        pageSlow.go();
        assertThat(pageSlow.getText()).isEqualTo("new");
    }

    @Test(expected = NoSuchElementException.class)
    public void when_ajax_fields_are_slower_than_timeout_then_NoSuchElementException_is_thrown() {
        pageTooSlow.go();
        assertThat(pageTooSlow.getText()).isEqualTo("new");
    }

    @Test(expected = NoSuchElementException.class)
    public void when_ajax_fields_are_considered_as_normal_fields_then_NoSuchElementException_is_thrown() {
        pageWithoutAjax.go();
        assertThat(pageWithoutAjax.getText()).isEqualTo("new");
    }


    private static class JavascriptPage extends FluentPage {

        @AjaxElement
        FluentWebElement newField;

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }

        public String getText() {
            return newField.getText();
        }
    }

    private static class JavascriptPageSlow extends FluentPage {

        @AjaxElement(timeOutInSeconds = 12)
        FluentWebElement newFieldSlow;

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }

        public String getText() {
            return newFieldSlow.getText();
        }
    }

    private static class JavascriptPageTooSlow extends FluentPage {

        @AjaxElement
        FluentWebElement newFieldSlow;

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }

        public String getText() {
            return newFieldSlow.getText();
        }
    }


    private static class JavascriptPageWithoutAjax extends FluentPage {

        FluentWebElement newField;

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }

        public String getText() {

            newField.click();

            return newField.getText();
        }
    }
}

