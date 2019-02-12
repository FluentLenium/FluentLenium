package org.fluentlenium.examples.test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.ReactDemoPage;
import org.junit.Before;
import org.junit.Test;

public class ReactDemoTest extends AbstractChromeTest {

    @Page
    private ReactDemoPage reactDemoPage;

    @Before
    public void setUp() {
        goTo(reactDemoPage).isAt();
    }

    @Test
    public void shouldClearSingleTextInput() {
        reactDemoPage.getUsernameInput().clearReactInput();
        assertThat(reactDemoPage.getUsernameInput()).hasValue("");
    }

    @Test
    public void shouldClearSinglePasswordInput() {
        reactDemoPage.getPasswordInput().clearReactInput();
        assertThat(reactDemoPage.getPasswordInput()).hasValue("");
    }

    @Test
    public void shouldClearAllTextInputs() {
        reactDemoPage.clearAllTextInputs();

        assertThat(reactDemoPage.getUsernameInput()).hasValue("");
        assertThat(reactDemoPage.getEmailInput()).hasValue("");
        assertThat(reactDemoPage.getConfirmEmailInput()).hasValue("");
    }

}
