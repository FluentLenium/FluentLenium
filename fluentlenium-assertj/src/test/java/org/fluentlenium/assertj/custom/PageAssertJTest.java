package org.fluentlenium.assertj.custom;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.page.ClassAnnotations;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PageAssertJTest {
    @Mock
    private FluentWebElement element;

    @Mock
    private FluentPage fluentPage;

    @Mock
    private FluentList<FluentWebElement> list;

    @Mock
    private WebDriver driver;

    @Mock
    private ClassAnnotations classAnnotations;

    private PageAssert pageAssert;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        pageAssert = assertThat(fluentPage);
        doReturn(driver).when(fluentPage).getDriver();
    }

    @Test
    public void hasElementOk() {
        when(element.present()).thenReturn(true);
        pageAssert.hasElement(element);
    }


    @Test(expectedExceptions = AssertionError.class)
    public void hasElementKo() {
        when(element.present()).thenReturn(false);
        pageAssert.hasElement(element);
    }

    @Test
    public void hasElementDisplayedOk() {
        when(element.displayed()).thenReturn(true);
        pageAssert.hasElementDisplayed(element);
    }


    @Test(expectedExceptions = AssertionError.class)
    public void hasElementDisplayedKo() {
        when(element.displayed()).thenReturn(false);
        pageAssert.hasElementDisplayed(element);
    }

    @Test
    public void hasElementsOk() {
        when(list.isEmpty()).thenReturn(false);
        pageAssert.hasElements(list);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void hasElementsKo() {
        when(list.isEmpty()).thenReturn(true);
        pageAssert.hasElements(list);
    }

    @Test
    public void hasTitleOk() {
        String title = "title";
        doReturn(title).when(driver).getTitle();
        pageAssert.hasTitle(title);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void hasTitleKo() {
        doReturn("title").when(driver).getTitle();
        pageAssert.hasTitle("wrong");
    }

    @Test
    public void hasTitleShouldFailDueToNullPointerException() {
        doReturn(null).when(fluentPage).getDriver();
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasTitle("non-existent")).hasMessage("Current page has no title");
    }

    @Test
    public void hasUrlOk() {
        String url = "https://fluentlenium.com";
        doReturn(url).when(driver).getCurrentUrl();
        pageAssert.hasUrl(url);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void hasUrlKo() {
        doReturn("https://fluentlenium.com").when(driver).getCurrentUrl();
        pageAssert.hasUrl("https://awesome-testing.com");
    }

    @Test
    public void hasPageSourceContainingOk() {
        String source = "<html></html>";
        doReturn(source).when(driver).getPageSource();
        pageAssert.hasPageSourceContaining(source);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void hasPageSourceContainingKo() {
        doReturn("<html></html>").when(driver).getPageSource();
        pageAssert.hasPageSourceContaining("<body>");
    }

    @Test
    public void testIsAt() {
        pageAssert.isAt();
        verify(fluentPage).isAt();
    }

    @Test
    public void testHasExpectedUrl() {
        String url = "https://fluentlenium.com";
        when(fluentPage.getUrl()).thenReturn(url);
        pageAssert.hasExpectedUrl();
        verify(fluentPage).isAtUsingUrl(url);
    }

    @Test
    public void testHasExpectedElements() {
        when(fluentPage.getClassAnnotations()).thenReturn(classAnnotations);
        By selector = By.cssSelector("selector");
        when(classAnnotations.buildBy()).thenReturn(selector);
        pageAssert.hasExpectedElements();
        verify(fluentPage).isAtUsingSelector(selector);
    }

    @Test
    public void testAssertMethodInherited() {
        when(fluentPage.getUrl()).thenReturn("http://lOcAlHOST/");
        assertThat(fluentPage.getUrl()).containsIgnoringCase("localhost");
    }
}
