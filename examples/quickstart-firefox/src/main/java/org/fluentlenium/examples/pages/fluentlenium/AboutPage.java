package org.fluentlenium.examples.pages.fluentlenium;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class AboutPage extends FluentPage {

    @FindBy(className = "username")
    private FluentList<FluentWebElement> contributors;

    @Override
    public void isAt() {
        assertThat(contributors).hasSize().greaterThan(0);
    }

    public void verifySlawomirPresence() {
        assertThat(contributors.texts()).contains("Sławomir Radzymiński");
    }

}
