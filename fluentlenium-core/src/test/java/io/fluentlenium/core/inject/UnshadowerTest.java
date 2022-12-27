package io.fluentlenium.core.inject;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.annotation.Unshadow;
import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnshadowerTest extends FluentPage {

    @Mock
    private TestWebDriver webDriver;

    @Mock
    private FluentControl fluentControl;

    @Mock
    private WebElement shadowRoots;

    @Mock
    private WebElement searchedElement1, searchedElement2;

    @Page
    private TestedWebpage webpage;

    Unshadower unshadower;

    @Before
    public void setUp() throws Exception {
        webpage = new TestedWebpage(fluentControl);
        unshadower = new Unshadower(webDriver, webpage);
        setMocks();
    }

    private void setMocks() {
        when(fluentControl.getDriver()).thenReturn(webDriver);

        when(webDriver.findElement(By.xpath("/*"))).thenReturn(shadowRoots);
        when(webDriver.executeScript(anyString(), any())).thenReturn(shadowRoots);

        when(shadowRoots.findElements(or(
                eq(By.cssSelector("outer-shadow-root")),
                eq(By.cssSelector("inner-shadow-root")))))
                .thenReturn(newArrayList(shadowRoots));
        when(shadowRoots.findElements(By.cssSelector("div"))).thenReturn(newArrayList(searchedElement1, searchedElement2));
        when(shadowRoots.findElements(By.xpath("/*"))).thenReturn(newArrayList(searchedElement1, searchedElement2));

        when(searchedElement1.getText()).thenReturn("DIV1");
        when(searchedElement2.getText()).thenReturn("DIV2");
    }

    @Test
    public void shouldExtractElementsFromShadowRoots() {
        unshadower.unshadowAllAnnotatedFields();

        Assertions.assertThat(webpage.getElement().text()).isEqualTo("DIV1");
        Assertions.assertThat(webpage.getElementsList())
                .hasSize(2)
                .extracting(FluentWebElement::text).containsExactly("DIV1", "DIV2");
        assertThat(webpage.getElementsSet())
                .hasSize(2)
                .extracting(FluentWebElement::text).containsExactlyInAnyOrder("DIV1", "DIV2");
    }

}

interface TestWebDriver extends WebDriver, JavascriptExecutor {
}

class TestedWebpage extends FluentPage {

    @Unshadow(css = {"outer-shadow-root", "inner-shadow-root", "div"})
    private FluentWebElement element;

    @Unshadow(css = {"inner-shadow-root", "div"})
    private List<FluentWebElement> elementsList;

    @Unshadow(css = {"div"})
    private Set<FluentWebElement> elementsSet;

    TestedWebpage(FluentControl control) {
        super(control);
    }

    public FluentWebElement getElement() {
        return element;
    }

    public List<FluentWebElement> getElementsList() {
        return elementsList;
    }

    public Set<FluentWebElement> getElementsSet() {
        return elementsSet;
    }

}
