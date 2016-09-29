package org.fluentlenium.core.hook;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.LocatorHandler;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.search.Search;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class SearchHookTest {
    @Mock
    WebElement element;

    @Mock
    WebDriver driver;

    DefaultComponentInstantiator instantiator;

    private Search search;

    @Before
    public void before() {
        FluentAdapter fluentAdapter = new FluentAdapter(driver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(driver, instantiator);

        Mockito.when(driver.findElements(By.cssSelector(".selector"))).thenReturn(Arrays.asList(element));
    }

    @Test
    public void testHookedSearch() {
        FluentWebElement hookedElement = search.el(".selector").withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getHookElement();

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }
}
