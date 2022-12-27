package io.fluentlenium.example.spring.page;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/javadoc")
public class JavadocPage extends FluentPage {

    @FindBy(className = "contentContainer")
    private FluentWebElement content;

   public void verifyIfIsLoaded() {
        assertThat(content).isPresent();
   }
}
