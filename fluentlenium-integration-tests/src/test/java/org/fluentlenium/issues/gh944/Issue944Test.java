package org.fluentlenium.issues.gh944;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class Issue944Test extends IntegrationFluentTest {
    @Test
    public void firstMethodShouldInitializeComponentFields() {
        goTo("https://google.com");

        FluentWebElement el = newInstance(GooglePage.class)
                .getInputs()
                .get(0)
                .getEl();

        FluentWebElement elFirst = newInstance(GooglePage.class)
                .getInputs()
                .first()
                .getEl();

        assertThat(el).isNotNull();
        assertThat(elFirst).isNotNull();
    }

    @Test
    public void lastMethodShouldInitializeComponentFields() {
        goTo("https://google.com");

        FluentWebElement el = newInstance(GooglePage.class)
                .getInputs()
                .get(1)
                .getEl();

        FluentWebElement elLast = newInstance(GooglePage.class)
                .getInputs()
                .last()
                .getEl();

        assertThat(el).isNotNull();
        assertThat(elLast).isNotNull();
    }
}

class GooglePage extends FluentPage {
    @FindBy(tagName = "center")
    private FluentList<CenteredElement> centeredElements;

    FluentList<CenteredElement> getInputs() {
        return centeredElements;
    }
}

class CenteredElement extends FluentWebElement {
    @FindBy(tagName = "input")
    private FluentWebElement el;

    CenteredElement(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
        super(element, control, instantiator);
    }

    FluentWebElement getEl() {
        return el;
    }
}
