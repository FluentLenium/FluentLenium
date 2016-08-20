package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindByOfComponentTest extends LocalFluentCase {

    @Page
    private PageIndex page;

    public static class SomeFluentWebElement extends FluentWebElement {
        public SomeFluentWebElement(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
            super(webElement, driver, instantiator);
        }
    }

    public static class SomeWebElementWrapper {
        private final WebElement element;
        private final WebDriver driver;

        public SomeWebElementWrapper(WebElement webElement) {
            this.element = webElement;
            this.driver = null;
        }

        public SomeWebElementWrapper(WebElement webElement, WebDriver driver) {
            this.element = webElement;
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
    public void testFluentWebElement() {
        page.go();
        page.isAt();

        assertThat(page.element).isInstanceOf(SomeFluentWebElement.class);
    }

    @Test
    public void testWebElementWrapper() {
        page.go();
        page.isAt();

        assertThat(page.wrapper).isInstanceOf(SomeWebElementWrapper.class);
    }

    @Test
    public void testFluentWebElementList() {
        page.go();
        page.isAt();

        for (SomeFluentWebElement component : page.elementList) {
            assertThat(component).isInstanceOf(SomeFluentWebElement.class);
        }
    }

    @Test
    public void testFindByComponentList() {
        page.go();
        page.isAt();

        for (SomeWebElementWrapper component : page.wrapperList) {
            assertThat(component).isInstanceOf(SomeWebElementWrapper.class);
        }
    }

    private static class PageIndex extends FluentPage {

        @FindBy(className = "small")
        SomeFluentWebElement element;

        @FindBy(className = "small")
        SomeWebElementWrapper wrapper;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        List<SomeFluentWebElement> elementList;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        List<SomeWebElementWrapper> wrapperList;

        @Override
        public String getUrl() {
            return LocalFluentCase.DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat(getDriver().getTitle()).contains("Selenium");
        }
    }
}
