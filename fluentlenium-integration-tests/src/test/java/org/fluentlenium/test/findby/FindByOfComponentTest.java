package org.fluentlenium.test.findby;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FindByOfComponentTest extends IntegrationFluentTest {

    @Page
    private PageIndex page;

    public static class SomeFluentWebElement extends FluentWebElement {
        SomeFluentWebElement(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class SomeWebElementWrapper {
        private final WebElement element;
        private final WebDriver driver;

        SomeWebElementWrapper(WebElement webElement) {
            element = webElement;
            driver = null;
        }

        SomeWebElementWrapper(WebElement webElement, WebDriver driver) {
            element = webElement;
            this.driver = driver;
        }

        public WebElement getElement() {
            return element;
        }

        public WebDriver getDriver() {
            return driver;
        }
    }

    @Test
    void testFluentWebElement() {
        page.go();
        page.isAt();

        assertThat(page.element).isInstanceOf(SomeFluentWebElement.class);
    }

    @Test
    void testWebElementWrapper() {
        page.go();
        page.isAt();

        assertThat(page.wrapper).isInstanceOf(SomeWebElementWrapper.class);
    }

    @Test
    void testFluentWebElementList() {
        page.go();
        page.isAt();

        for (SomeFluentWebElement component : page.elementList) {
            assertThat(component).isInstanceOf(SomeFluentWebElement.class);
        }
    }

    @Test
    void testFindByComponentList() {
        page.go();
        page.isAt();

        for (SomeWebElementWrapper component : page.wrapperList) {
            assertThat(component).isInstanceOf(SomeWebElementWrapper.class);
        }
    }

    private static class PageIndex extends FluentPage {

        @FindBy(className = "small")
        private SomeFluentWebElement element;

        @FindBy(className = "small")
        private SomeWebElementWrapper wrapper;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private List<SomeFluentWebElement> elementList;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private List<SomeWebElementWrapper> wrapperList;

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
