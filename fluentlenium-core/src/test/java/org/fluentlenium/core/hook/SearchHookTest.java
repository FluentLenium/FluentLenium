package org.fluentlenium.core.hook;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
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
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.when;

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
        FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(driver, this, instantiator, fluentAdapter);

        when(driver.findElements(By.cssSelector(".selector"))).thenReturn(Arrays.asList(element));
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
    }

    @Test
    public void testHookedSearch() {
        FluentWebElement hookedElement = search.el(".selector").withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstAfter() {
        FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstBefore() {
        FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHook() {
        FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).noHook().click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        WebElement hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }

    @Test
    public void testHookSearchNoHookClickAndRestore() {
        FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).noHook().click().restoreHooks();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchHookBeforeFirstNoHookClickAndRestore() {
        FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().noHook().click().restoreHooks();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHookFunction() {
        FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first()
                .noHook(new Function<FluentWebElement, FluentWebElement>() {
                    @Override
                    public FluentWebElement apply(FluentWebElement input) {
                        return input.click();
                    }
                });

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstNoHookFunction() {
        FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class)
                .noHook(new Function<FluentWebElement, FluentWebElement>() {
                    @Override
                    public FluentWebElement apply(FluentWebElement input) {
                        return input.click();
                    }
                });

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHookClone() {
        FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().noHookInstance().click();

        Mockito.verify(element).click();

        LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        WebElement hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }

    @Test
    public void testHookSearchListNoHookClone() {
        FluentList<FluentWebElement> hookedElement = search.$(".selector").withHook(NanoHook.class).noHookInstance().click();

        Mockito.verify(element).click();

        LocatorHandler<List<WebElement>> componentHandler = LocatorProxies.getLocatorHandler(hookedElement);
        List<WebElement> hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }
}
