package io.fluentlenium.test.findby;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.Assertions;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

class FindByOfListTest extends IntegrationFluentTest {

    @Page
    private PageIndex page;

    @Test
    void shouldFindByRetrieveList() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.smalls).hasSize(3);
        Assertions.assertThat(page.smalls.texts()).containsExactly("Small 1", "Small 2", "Small 3");
    }

    @Test
    void shouldFindAllFindByRetrieveList() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.findAllElements).hasSize(4);
        Assertions.assertThat(page.findAllElements.texts()).containsExactly("Pharmacy", "Small 1", "Small 2", "Small 3");
    }

    private static class PageIndex extends FluentPage {

        @FindBy(className = "small")
        private FluentList<FluentWebElement> smalls;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private FluentList<FluentWebElement> findAllElements;

        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat(getDriver().getTitle()).contains("Selenium");
        }
    }
}


