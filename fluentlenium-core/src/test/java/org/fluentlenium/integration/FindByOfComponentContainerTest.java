package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindByOfComponentContainerTest extends IntegrationFluentTest {

    @Page
    private ContainerIndex page;

    public static class SomeFluentWebElement extends FluentWebElement {
        public SomeFluentWebElement(WebElement webElement, FluentControl fluentControl,
                ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class SomeWebElementWrapper {
        private final WebElement element;
        private final WebDriver driver;

        public SomeWebElementWrapper(WebElement webElement) {
            element = webElement;
            driver = null;
        }

        public SomeWebElementWrapper(WebElement webElement, WebDriver driver) {
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
    public void testFluentWebElement() {
        goTo(IntegrationFluentTest.DEFAULT_URL);

        assertThat(page.element).isInstanceOf(SomeFluentWebElement.class);
    }

    @Test
    public void testWebElementWrapper() {
        goTo(IntegrationFluentTest.DEFAULT_URL);

        assertThat(page.wrapper).isInstanceOf(SomeWebElementWrapper.class);
    }

    @Test
    public void testFluentWebElementList() {
        goTo(IntegrationFluentTest.DEFAULT_URL);

        for (SomeFluentWebElement component : page.elementList) {
            assertThat(component).isInstanceOf(SomeFluentWebElement.class);
        }
    }

    @Test
    public void testFindByComponentList() {
        goTo(IntegrationFluentTest.DEFAULT_URL);

        for (SomeWebElementWrapper component : page.wrapperList) {
            assertThat(component).isInstanceOf(SomeWebElementWrapper.class);
        }
    }

    private static class ContainerIndex {

        @FindBy(className = "small")
        private SomeFluentWebElement element;

        @FindBy(className = "small")
        private SomeWebElementWrapper wrapper;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private List<SomeFluentWebElement> elementList;

        @FindAll({@FindBy(id = "location"), @FindBy(className = "small")})
        private List<SomeWebElementWrapper> wrapperList;
    }
}
