package io.fluentlenium.core.search;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.DefaultComponentInstantiator;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.filter.AttributeFilter;
import org.assertj.core.api.Assertions;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.DefaultComponentInstantiator;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.filter.AttributeFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchTest {
    @Mock
    private WebDriver driver;

    @Mock
    private SearchContext searchContext;

    @Mock
    private AttributeFilter filter1;

    @Mock
    private AttributeFilter filter2;

    private Search search;

    @Before
    public void before() {
        FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        DefaultComponentInstantiator instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(searchContext, this, instantiator, fluentAdapter);
    }

    @After
    public void after() {
        reset(searchContext);
    }

    @Test
    public void findCheckCssIsWellFormed() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = new ArrayList<>(Arrays.asList(webElement, webElement2));

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"))).thenReturn(webElements);

        String name = "cssStyle";
        AttributeFilter[] filters = new AttributeFilter[]{filter1, filter2};
        when(filter1.isCssFilterSupported()).thenReturn(true);
        when(filter1.getCssFilter()).thenReturn("[generated=true]");
        when(filter2.isCssFilterSupported()).thenReturn(true);
        when(filter2.getCssFilter()).thenReturn("[checked=ok]");

        search.find(name, filters).now();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"));
    }

    @Test
    public void canLoopIntoFluentWebElementAfterASearch() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = new ArrayList<>(Arrays.asList(webElement, webElement2));

        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);
        for (FluentWebElement fluentWebElement : search.find("cssStyle")) {
            //just to check the cast
            assertThat(fluentWebElement).isInstanceOf(FluentWebElement.class);
        }

    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelector() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = new ArrayList<>(Arrays.asList(webElement, webElement2));

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        String name = "cssStyle";
        AttributeFilter[] filters = new AttributeFilter[]{filter1, filter2};
        when(filter1.isCssFilterSupported()).thenReturn(true);
        when(filter1.getCssFilter()).thenReturn("[generated=true]");
        when(filter2.isCssFilterSupported()).thenReturn(false);
        when(filter2.applyFilter(anyCollection())).thenAnswer((Answer<Collection<FluentWebElement>>)
                invocation -> (Collection<FluentWebElement>) invocation.getArguments()[0]);

        search.find(name, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true]"));
    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelectorAndByLocator() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = new ArrayList<>(Arrays.asList(webElement, webElement2));

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        By locator = By.cssSelector("cssStyle");
        AttributeFilter[] filters = new AttributeFilter[]{filter1, filter2};
        when(filter1.applyFilter(anyCollection())).thenAnswer((Answer<Collection<FluentWebElement>>)
                invocation -> (Collection<FluentWebElement>) invocation.getArguments()[0]);
        when(filter2.applyFilter(anyCollection())).thenAnswer((Answer<Collection<FluentWebElement>>)
                invocation -> (Collection<FluentWebElement>) invocation.getArguments()[0]);

        search.find(locator, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle")); // By parameter doesn't support CSS filtering.
    }

    @Test
    public void findPostSelectorFilterWithElementThatMatch() {
        String name = "cssStyle";
        AttributeFilter[] filters = new AttributeFilter[]{filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        when(filter1.applyFilter(anyCollection())).thenAnswer((Answer<Collection<FluentWebElement>>)
                invocation -> (Collection<FluentWebElement>) invocation.getArguments()[0]);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));

        FluentList fluentList = search.find(name, filters);
        assertThat(fluentList).hasSize(1);
    }

    @Test
    public void findPostSelectorFilterWithElementThatDontMatch() {
        String name = "cssStyle";
        AttributeFilter[] filters = new AttributeFilter[]{filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));

        assertThatThrownBy(() -> search.find(name, filters).now()).isExactlyInstanceOf(NoSuchElementException.class);

        Assertions.assertThat(search.find(name, filters).present()).isFalse();
        Assertions.assertThat(search.find(name, filters).optional()).isNotPresent();
    }

    @Test
    public void findPostSelectorFilterWithFilterOnText() {
        String name = "cssStyle";
        AttributeFilter[] filters = new AttributeFilter[]{filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));

        Assertions.assertThat(search.find(name, filters).present()).isFalse();
        Assertions.assertThat(search.find(name, filters).optional()).isNotPresent();

        verify(filter1, times(2)).applyFilter(any(Collection.class));
    }

    @Test
    public void findByPosition() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.find(name).index(1);
        assertThat(fluentWebElement.tagName()).isEqualTo("a");
    }

    @Test
    public void mobileFindByPosition() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        when(webElement1.getText()).thenReturn("text");
        List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(AppiumBy.accessibilityId(name))).thenReturn(webElements);

        FluentWebElement fluentWebElement1 = search.el(AppiumBy.accessibilityId(name));
        assertThat(fluentWebElement1.text()).isEqualTo("text");

        FluentWebElement fluentWebElement2 = search.find(AppiumBy.accessibilityId(name)).index(1);
        assertThat(fluentWebElement2.tagName()).isEqualTo("a");

        Assertions.assertThat(search.$(AppiumBy.accessibilityId(name))).hasSize(2);
    }

    @Test
    public void shouldThrowErrorWhenListMobileElementNotFound() {
        assertThatThrownBy(() -> search.find(AppiumBy.accessibilityId("something")).index(0).click())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("AppiumBy.accessibilityId")
                .hasMessageContaining("something");
    }

    @Test
    public void shouldThrowErrorWhen$ListMobileElementNotFound() {
        assertThatThrownBy(() -> search.$(AppiumBy.id("something")).first().text())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("By.id")
                .hasMessageContaining("something");
    }

    @Test
    public void shouldThrowErrorWhenMobileElementNotFound() {
        assertThatThrownBy(() -> search.el(AppiumBy.androidUIAutomator("dummy")).click())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("AppiumBy.androidUIAutomator")
                .hasMessageContaining("dummy");
    }

    @Test
    public void shouldThrowErrorWhenPositionNotFound() {
        assertThatThrownBy(() -> search.find("cssStyle").index(0).now())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("By.cssSelector")
                .hasMessageContaining("cssStyle");
    }

    @Test
    public void findFirstShouldReturnFirst() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        WebElement webElement2 = mock(WebElement.class);

        List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenFirstNotFound() {
        String name = "cssStyle";
        FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

    public void findWithRawSelenium() {
        WebElement mock = Mockito.mock(WebElement.class);

        FluentWebElement el = search.el(mock);

        assertThat(el.getElement()).isSameAs(mock);
    }
}
