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
    private WebElement element;

    @Mock
    private WebDriver driver;

    private DefaultComponentInstantiator instantiator;

    private Search search;

    @Before
    public void before() {
        final FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(driver, instantiator);

        Mockito.when(driver.findElements(By.cssSelector(".selector"))).thenReturn(Arrays.asList(element));
    }

    @Test
    public void testHookedSearch() {
        final FluentWebElement hookedElement = search.el(".selector").withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getHookElement();

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }
}
