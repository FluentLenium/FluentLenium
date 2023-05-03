package io.fluentlenium.core.search;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.DefaultComponentInstantiator;
import io.fluentlenium.core.filter.AttributeFilter;
import io.fluentlenium.core.filter.FilterConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class NoSuchElementMessageTest {
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

    @Test
    public void testListSelector() {
        assertThatThrownBy(() -> search.$("test").now())
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("Elements By.cssSelector: test (Lazy Element List) is not present");
    }

    @Test
    public void testElementSelector() {
        assertThatThrownBy(() -> search.el("test").now())
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("Element By.cssSelector: test (first) (Lazy Element) is not present");
    }

    @Test
    public void testListWithFilterSelector() {
        assertThatThrownBy(() -> search.$("test", FilterConstructor.withText("someText"), FilterConstructor.withId("someId")).now())
                .isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                        "Elements By.cssSelector: test[id=\"someId\"] with text equals to \"someText\""
                                + " (Lazy Element List) is not present");

    }

    @Test
    public void testListBySelectorWithFilterSelector() {
        assertThatThrownBy(() -> search.$(By.cssSelector("test"), FilterConstructor.withText("someText"), FilterConstructor.withId("someId")).now())
                .isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                        "Elements By.cssSelector: test with text equals to \"someText\" and with id equals to \"someId\" "
                                + "(Lazy Element List) is not present");

    }

    @Test
    public void testElWithFilterSelector() {
        assertThatThrownBy(() -> search.el("test", FilterConstructor.withText("someText"), FilterConstructor.withId("someId")).now())
                .isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                        "Element By.cssSelector: test[id=\"someId\"] with text equals to \"someText\" (first) (Lazy Element) "
                                + "is not present");
    }

    @Test
    public void testElBySelectorWithFilterSelector() {
        assertThatThrownBy(() -> search.el(By.cssSelector("test"), FilterConstructor.withText("someText"), FilterConstructor.withId("someId")).now())
                .isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                        "Element By.cssSelector: test with text equals to \"someText\" and with id equals to \"someId\" (first)"
                                + " (Lazy Element) is not present");
    }
}
