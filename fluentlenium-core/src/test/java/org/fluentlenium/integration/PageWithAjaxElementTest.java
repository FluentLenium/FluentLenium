package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class PageWithAjaxElementTest extends LocalFluentCase {
    @Page
    JavascriptPage page;

    @Page
    JavascriptPageWithoutAjax pageWithoutAjax;

    @Test
    public void when_ajax_fields_are_considered_as_ajax_fields_then_wait_for_them() {
        page.go();
        assertThat(page.getText()).isEqualTo("new");
    }

    @Test(expected =NoSuchElementException.class )
    public void when_ajax_fields_are_considered_as_normal_fields_then_an_noSuchElementException_is_thrown() {
        pageWithoutAjax.go();
        assertThat(pageWithoutAjax.getText()).isEqualTo("new");
    }



}

class JavascriptPage extends FluentPage {

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




class JavascriptPageWithoutAjax extends FluentPage {

    FluentWebElement newField;

    @Override
    public String getUrl() {
        return LocalFluentCase.JAVASCRIPT_URL;
    }

    public String getText() {

        click(newField);

        return newField.getText();
    }
}
