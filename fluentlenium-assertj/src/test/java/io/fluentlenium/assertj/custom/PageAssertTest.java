package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.page.ClassAnnotations;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PageAssert}.
 */
public class PageAssertTest {
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


    @Test
    public void hasElementKo() {
        when(element.present()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasElement(element))
                .hasMessage("Element element is not present on current page");
    }

    @Test
    public void hasElementDisplayedOk() {
        when(element.displayed()).thenReturn(true);
        pageAssert.hasElementDisplayed(element);
    }


    @Test
    public void hasElementDisplayedKo() {
        when(element.displayed()).thenReturn(false);
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasElementDisplayed(element))
                .hasMessage("Element element is not displayed on current page");
    }

    @Test
    public void hasElementsOk() {
        when(list.isEmpty()).thenReturn(false);
        pageAssert.hasElements(list);
    }

    @Test
    public void hasElementsKo() {
        when(list.isEmpty()).thenReturn(true);
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasElements(list))
                .hasMessage("No element selected by 'list' is present on the page.");
    }

    @Test
    public void hasTitleOk() {
        String title = "title";
        doReturn(title).when(driver).getTitle();
        pageAssert.hasTitle(title);
    }

    @Test
    public void hasTitleKo() {
        doReturn("title").when(driver).getTitle();
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasTitle("wrong"))
                .hasMessage("Current page title is title. Expected wrong");
    }

    @Test
    public void hasTitleShouldFailDueToNullPointerException() {
        doReturn(null).when(fluentPage).getDriver();
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasTitle("non-existent"))
                .hasMessage("Current page has no title");
    }

    @Test
    public void hasUrlOk() {
        String url = "https://fluentlenium.io";
        doReturn(url).when(driver).getCurrentUrl();
        pageAssert.hasUrl(url);
    }

    @Test
    public void hasUrlKo() {
        doReturn("https://fluentlenium.io").when(driver).getCurrentUrl();
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasUrl("https://awesome-testing.com"))
                .hasMessage("Current page url is https://fluentlenium.io. Expected https://awesome-testing.com");
    }

    @Test
    public void hasPageSourceContainingOk() {
        String source = "<html></html>";
        doReturn(source).when(driver).getPageSource();
        pageAssert.hasPageSourceContaining(source);
    }

    @Test
    public void hasPageSourceContainingKo() {
        doReturn("<html></html>").when(driver).getPageSource();
        assertThatAssertionErrorIsThrownBy(() -> pageAssert.hasPageSourceContaining("<body>"))
                .hasMessage("Current page source does not contain: <body>");
    }

    @Test
    public void testIsAt() {
        pageAssert.isAt();
        verify(fluentPage).isAt();
    }

    @Test
    public void testHasExpectedUrl() {
        String url = "https://fluentlenium.io";
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
