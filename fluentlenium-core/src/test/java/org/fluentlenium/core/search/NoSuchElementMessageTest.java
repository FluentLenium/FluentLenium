package org.fluentlenium.core.search;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.filter.AttributeFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

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
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.$("test").now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("Elements By.cssSelector: test (Lazy Element List) is not present");
    }

    @Test
    public void testElementSelector() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.el("test").now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("Element By.cssSelector: test (first) (Lazy Element) is not present");
    }

    @Test
    public void testListWithFilterSelector() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.$("test", withText("someText"), withId("someId")).now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                "Elements By.cssSelector: test[id=\"someId\"] with text equals to \"someText\" (Lazy Element List) is not "
                        + "present");

    }

    @Test
    public void testListBySelectorWithFilterSelector() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.$(By.cssSelector("test"), withText("someText"), withId("someId")).now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                "Elements By.cssSelector: test with text equals to \"someText\" and with id equals to \"someId\" (Lazy Element "
                        + "List) is not present");

    }

    @Test
    public void testElWithFilterSelector() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.el("test", withText("someText"), withId("someId")).now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                "Element By.cssSelector: test[id=\"someId\"] with text equals to \"someText\" (first) (Lazy Element) is not "
                        + "present");
    }

    @Test
    public void testElBySelectorWithFilterSelector() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.el(By.cssSelector("test"), withText("someText"), withId("someId")).now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).hasMessageStartingWith(
                "Element By.cssSelector: test with text equals to \"someText\" and with id equals to \"someId\" (first) (Lazy "
                        + "Element) is not present");
    }
}
