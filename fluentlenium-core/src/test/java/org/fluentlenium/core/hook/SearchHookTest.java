package org.fluentlenium.core.hook;

import com.google.common.base.Function;
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
        final FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(driver, instantiator);

        when(driver.findElements(By.cssSelector(".selector"))).thenReturn(Arrays.asList(element));
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
    }

    @Test
    public void testHookedSearch() {
        final FluentWebElement hookedElement = search.el(".selector").withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstAfter() {
        final FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstBefore() {
        final FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isNotEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isNotEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHook() {
        final FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).noHook().click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        WebElement hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }

    @Test
    public void testHookSearchNoHookClickAndRestore() {
        final FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class).noHook().click()
                .restoreHooks();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchHookBeforeFirstNoHookClickAndRestore() {
        final FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().noHook().click()
                .restoreHooks();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHookFunction() {
        final FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first()
                .noHook(new Function<FluentWebElement, FluentWebElement>() {
                    @Override
                    public FluentWebElement apply(final FluentWebElement input) {
                        return input.click();
                    }
                });

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchFirstNoHookFunction() {
        final FluentWebElement hookedElement = search.$(".selector").first().withHook(NanoHook.class)
                .noHook(new Function<FluentWebElement, FluentWebElement>() {
                    @Override
                    public FluentWebElement apply(final FluentWebElement input) {
                        return input.click();
                    }
                });

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final NanoHook hookElement = (NanoHook) componentHandler.getInvocationTarget(null);

        Assertions.assertThat(hookElement.getBeforeClickNano()).isEqualTo(0L);
        Assertions.assertThat(hookElement.getAfterClickNano()).isEqualTo(0L);
    }

    @Test
    public void testHookSearchNoHookClone() {
        final FluentWebElement hookedElement = search.$(".selector").withHook(NanoHook.class).first().noHookInstance().click();

        Mockito.verify(element).click();

        final LocatorHandler<WebElement> componentHandler = LocatorProxies.getLocatorHandler(hookedElement.getElement());
        final WebElement hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }

    @Test
    public void testHookSearchListNoHookClone() {
        final FluentList<FluentWebElement> hookedElement = search.$(".selector").withHook(NanoHook.class).noHookInstance()
                .click();

        Mockito.verify(element).click();

        final LocatorHandler<List<WebElement>> componentHandler = LocatorProxies.getLocatorHandler(hookedElement);
        final List<WebElement> hookElement = componentHandler.getInvocationTarget(null);
        Assertions.assertThat(hookElement).isNotInstanceOf(NanoHook.class);
    }
}
