package org.fluentlenium.examples.test.react;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.react.ReactDemoPage;
import org.fluentlenium.examples.test.AbstractChromeTest;
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

    @Test
    public void verifyPageAssertions() {
        assertThat(reactDemoPage).hasTitle("MobX React Form");
        assertThat(reactDemoPage).hasPageSourceContaining("light-red");
        assertThat(reactDemoPage).hasElements($("button"));
        assertThat(reactDemoPage).hasElement(el("[name=email]"));
        assertThat(reactDemoPage).hasElementDisplayed(el("[name=email]"));
        assertThat(reactDemoPage).hasExpectedElements();
        assertThat(reactDemoPage).hasExpectedUrl();
        assertThat(reactDemoPage).hasUrl("https://foxhound87.github.io/mobx-react-form-demo/demo");
    }

}
