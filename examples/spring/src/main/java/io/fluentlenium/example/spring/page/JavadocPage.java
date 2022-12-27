package org.fluentlenium.example.spring.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/javadoc")
public class JavadocPage extends FluentPage {

    @FindBy(className = "contentContainer")
    private FluentWebElement content;

   public void verifyIfIsLoaded() {
        assertThat(content).isPresent();
   }
}
