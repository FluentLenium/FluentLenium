package io.fluentlenium.core.inject;

import io.fluentlenium.adapter.FluentAdapter;import io.fluentlenium.core.FluentControl;import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.core.components.ComponentInstantiator;import io.fluentlenium.core.components.ComponentsManager;import io.fluentlenium.core.domain.FluentList;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.hook.Hook;import io.fluentlenium.core.hook.HookOptions;import io.fluentlenium.core.hook.NoHook;import io.fluentlenium.core.proxy.LocatorHandler;import io.fluentlenium.core.proxy.LocatorProxies;import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.hook.Hook;
import io.fluentlenium.core.hook.HookOptions;
import io.fluentlenium.core.hook.NanoHook;
import io.fluentlenium.core.hook.NanoHookAnnotation;
import io.fluentlenium.core.hook.NanoHookOptions;
import io.fluentlenium.core.hook.NoHook;
import io.fluentlenium.core.proxy.LocatorHandler;
import io.fluentlenium.core.proxy.LocatorProxies;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentInjectorHookTest {

    @Mock
    private WebDriver webDriver;

    private FluentAdapter fluentAdapter;

    private FluentInjector injector;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, null, new ComponentsManager(fluentAdapter),
                new DefaultContainerInstantiator(fluentAdapter));
    }

    @After
    public void after() {
        reset(webDriver);
    }

    public static class FluentWebElementContainer {
        @Hook(NanoHook.class)
        private FluentWebElement injected;
    }

    @Test
    public void testFluentWebElement() {
        FluentWebElementContainer container = new FluentWebElementContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isNull();

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);
    }

    public static class Options extends NanoHookOptions {
        public Options() {
            super("options");
        }
    }

    public static class FluentWebElementOptionContainer {
        @Hook(NanoHook.class)
        @HookOptions(Options.class)
        private FluentWebElement injected;
    }

    @Test
    public void testFluentWebElementOption() {
        FluentWebElementOptionContainer container = new FluentWebElementOptionContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isEqualTo("options");

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);
    }

    public static class WebElementWrapper {
        private final WebElement element;

        public WebElementWrapper(WebElement element) {
            this.element = element;
        }

        public WebElement getElement() {
            return element;
        }
    }

    public static class WebElementWrapperContainer {
        @Hook(NanoHook.class)
        private WebElementWrapper injected;
    }

    @Test
    public void testWebElementWrapper() {
        WebElementWrapperContainer container = new WebElementWrapperContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);
    }

    public static class FluentListContainer {
        @Hook(NanoHook.class)
        private FluentList<FluentWebElement> injected;
    }

    @Test
    public void testFluentList() {
        FluentListContainer container = new FluentListContainer();

        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        WebElement element3 = mock(WebElement.class);

        when(webDriver.findElements(new ByIdOrName("injected"))).thenReturn(Arrays.asList(element1, element2, element3));

        injector.inject(container);

        LocatorHandler listLocatorHandler = LocatorProxies.getLocatorHandler(container.injected);
        NanoHook listLocatorHook = (NanoHook) listLocatorHandler.getHookLocator();

        assertThat(listLocatorHook.getBeforeFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.injected).hasSize(3);

        assertThat(listLocatorHook.getBeforeFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getBeforeFindElementsNano()).isNotEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementsNano()).isNotEqualTo(0L);

        for (FluentWebElement webElement : container.injected) {
            assertThat(webElement).isNotNull();

            webElement.click();
            verify(LocatorProxies.getLocatorResult(webElement.getElement())).click();

            LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(webElement.getElement());
            NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

            assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
            assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);

            assertThat(elementWrapperHook.getBeforeFindElementNano()).isEqualTo(0L);
            assertThat(elementWrapperHook.getAfterFindElementNano()).isEqualTo(0L);
            assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
            assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);
        }
    }

    public static class WebElementWrapperListContainer {
        @Hook(NanoHook.class)
        private List<WebElementWrapper> injected;
    }

    @Test
    public void testWebElementWrapperList() {
        WebElementWrapperListContainer container = new WebElementWrapperListContainer();

        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        WebElement element3 = mock(WebElement.class);

        when(webDriver.findElements(new ByIdOrName("injected"))).thenReturn(Arrays.asList(element1, element2, element3));

        injector.inject(container);

        LocatorHandler listLocatorHandler = LocatorProxies.getLocatorHandler(container.injected);
        NanoHook listLocatorHook = (NanoHook) listLocatorHandler.getHookLocator();

        assertThat(listLocatorHook.getBeforeFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.injected).hasSize(3);

        assertThat(listLocatorHook.getBeforeFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementNano()).isEqualTo(0L);
        assertThat(listLocatorHook.getBeforeFindElementsNano()).isNotEqualTo(0L);
        assertThat(listLocatorHook.getAfterFindElementsNano()).isNotEqualTo(0L);

        for (WebElementWrapper webElement : container.injected) {
            assertThat(webElement).isNotNull();

            webElement.getElement().click();
            verify(LocatorProxies.getLocatorResult(webElement.getElement())).click();

            LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(webElement.getElement());
            NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

            assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
            assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        }
    }

    public static class NanoHook2 extends NanoHook {
        public NanoHook2(FluentControl fluentControl, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier,
                Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier, NanoHookOptions options) {
            super(fluentControl, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
        }
    }

    @NoHook
    public static class SubContainer3 {
        private WebElementWrapper subInjected3;
    }

    @Hook(NanoHook2.class)
    public static class SubContainer2 extends FluentPage {
        private WebElementWrapper subInjected2;

        @NoHook(@Hook(NanoHook.class))
        private WebElementWrapper subInjected2NoHook;
    }

    public static class SubContainer {
        private FluentWebElement subInjected;

        @NoHook
        private FluentWebElement subNoHookInjected;

        @Page
        private SubContainer2 subContainer2;

        @Page
        private SubContainer3 subContainer3;
    }

    @Hook(NanoHook.class)
    public static class FluentWebElementClassContainer {
        private FluentWebElement injected;

        @Page
        private SubContainer subContainer;
    }

    @Test
    public void testFluentWebElementClass() { // NOPMD ExcessiveMethodLength
        FluentWebElementClassContainer container = new FluentWebElementClassContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        WebElement subElement = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("subInjected"))).thenReturn(subElement);

        WebElement subNoHookElement = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("subNoHookInjected"))).thenReturn(subNoHookElement);

        WebElement subElement2 = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("subInjected2"))).thenReturn(subElement2);

        WebElement subElement2NoHook = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("subInjected2NoHook"))).thenReturn(subElement2NoHook);

        WebElement subElement3 = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("subInjected3"))).thenReturn(subElement3);

        WebElement subElementMethod = mock(WebElement.class);
        when(webDriver.findElements(By.cssSelector("#subInjectedMethod"))).thenReturn(singletonList(subElementMethod));

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isNull();

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.subContainer.subInjected).isNotNull();

        container.subContainer.subInjected.getElement().click();
        verify(subElement).click();

        LocatorHandler subElementWrapperHandler = LocatorProxies
                .getLocatorHandler(container.subContainer.subInjected.getElement());
        NanoHook subElementWrapperHook = (NanoHook) subElementWrapperHandler.getInvocationTarget(null);

        assertThat(subElementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(subElementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(subElementWrapperHook.getOptionValue()).isNull();

        assertThat(subElementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(subElementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(subElementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(subElementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);

        container.subContainer.subNoHookInjected.getElement().click();
        verify(subNoHookElement).click();

        LocatorHandler subNoHookElementWrapperHandler = LocatorProxies
                .getLocatorHandler(container.subContainer.subNoHookInjected.getElement());
        assertThat(subNoHookElementWrapperHandler.getInvocationTarget(null))
                .isSameAs(subNoHookElementWrapperHandler.getLocatorResult());

        container.subContainer.subContainer2.subInjected2.getElement().click();
        verify(subElement2).click();

        LocatorHandler subElement2WrapperHandler = LocatorProxies
                .getLocatorHandler(container.subContainer.subContainer2.subInjected2.getElement());
        assertThat(subElement2WrapperHandler.getInvocationTarget(null)).isExactlyInstanceOf(NanoHook2.class);

        NanoHook2 nanoHook2 = (NanoHook2) subElement2WrapperHandler.getInvocationTarget(null);

        assertThat(nanoHook2.getElement()).isExactlyInstanceOf(NanoHook.class);
        assertThat(((NanoHook) nanoHook2.getElement()).getElement()).isSameAs(subElement2);

        container.subContainer.subContainer2.subInjected2NoHook.getElement().click();
        verify(subElement2NoHook).click();

        LocatorHandler subElement2NoHookWrapperHandler = LocatorProxies
                .getLocatorHandler(container.subContainer.subContainer2.subInjected2NoHook.getElement());
        assertThat(subElement2NoHookWrapperHandler.getInvocationTarget(null)).isExactlyInstanceOf(NanoHook2.class);

        NanoHook2 nanoHook2NoHook = (NanoHook2) subElement2NoHookWrapperHandler.getInvocationTarget(null);

        assertThat(nanoHook2NoHook.getElement()).isSameAs(subElement2NoHook);

        container.subContainer.subContainer3.subInjected3.getElement().click();
        verify(subElement3).click();

        LocatorHandler subNoHook3ElementWrapperHandler = LocatorProxies
                .getLocatorHandler(container.subContainer.subContainer3.subInjected3.getElement());
        assertThat(subNoHook3ElementWrapperHandler.getInvocationTarget(null))
                .isSameAs(subNoHook3ElementWrapperHandler.getLocatorResult());

        WebElementWrapper subInjectedMethod = container.subContainer.subContainer2.find("#subInjectedMethod").first()
                .as(WebElementWrapper.class);
        LocatorProxies.now(subInjectedMethod.getElement());

        LocatorHandler subInjectedMethodHandler = LocatorProxies.getLocatorHandler(subInjectedMethod.getElement());
        assertThat(subInjectedMethodHandler.getInvocationTarget(null)).isExactlyInstanceOf(NanoHook2.class);

        WebElementWrapper subInjectedMethodNoHook = container.subContainer.subContainer2.find("#subInjectedMethod").first()
                .noHook().as(WebElementWrapper.class);
        LocatorProxies.now(subInjectedMethodNoHook.getElement());

        LocatorHandler subInjectedMethodNoHookHandler = LocatorProxies.getLocatorHandler(subInjectedMethodNoHook.getElement());
        assertThat(subInjectedMethodNoHookHandler.getInvocationTarget(null)).isSameAs(subElementMethod);

        WebElementWrapper subInjectedMethodNoHook2 = container.subContainer.subContainer2.find("#subInjectedMethod").noHook()
                .first().as(WebElementWrapper.class);
        LocatorProxies.now(subInjectedMethodNoHook2.getElement());

        LocatorHandler subInjectedMethodNoHook2Handler = LocatorProxies.getLocatorHandler(subInjectedMethodNoHook2.getElement());
        assertThat(subInjectedMethodNoHook2Handler.getInvocationTarget(null)).isSameAs(subElementMethod);
    }

    @NanoHookAnnotation("test")
    public static class FluentWebElementAnnotationContainer {
        protected FluentWebElement injected;

        @NoHook
        protected FluentWebElement injectedNoHook;
    }

    @Test
    public void testFluentWebElementAnnotationContainer() {
        FluentWebElementAnnotationContainer container = new FluentWebElementAnnotationContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        WebElement elementNoHook = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injectedNoHook"))).thenReturn(elementNoHook);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isEqualTo("test");

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.injectedNoHook).isNotNull();

        container.injectedNoHook.getElement().click();
        verify(elementNoHook).click();

        LocatorHandler elementNoHookHandler = LocatorProxies.getLocatorHandler(container.injectedNoHook.getElement());
        WebElement elementNoHookLocatorResult = (WebElement) elementNoHookHandler.getInvocationTarget(null);

        assertThat(elementNoHookLocatorResult).isSameAs(elementNoHook);
    }

    public static class FluentWebElementAnnotation {
        @NanoHookAnnotation("test")
        private FluentWebElement injected;
    }

    @Test
    public void testFluentWebElementAnnotation() {
        FluentWebElementAnnotation container = new FluentWebElementAnnotation();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isEqualTo("test");

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);
    }

    public static class FluentWebElementExtendsContainer extends FluentWebElementAnnotationContainer {
        private FluentWebElement injected2;
    }

    @Test
    public void testFluentWebElementExtendsContainer() {
        FluentWebElementExtendsContainer container = new FluentWebElementExtendsContainer();

        WebElement element = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected"))).thenReturn(element);

        WebElement element2 = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injected2"))).thenReturn(element2);

        WebElement elementNoHook = mock(WebElement.class);
        when(webDriver.findElement(new ByIdOrName("injectedNoHook"))).thenReturn(elementNoHook);

        injector.inject(container);

        assertThat(container.injected).isNotNull();

        container.injected.getElement().click();
        verify(element).click();

        LocatorHandler elementWrapperHandler = LocatorProxies.getLocatorHandler(container.injected.getElement());
        NanoHook elementWrapperHook = (NanoHook) elementWrapperHandler.getInvocationTarget(null);

        assertThat(elementWrapperHook.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getOptionValue()).isEqualTo("test");

        assertThat(elementWrapperHook.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.injected2).isNotNull();

        container.injected2.getElement().click();
        verify(element2).click();

        LocatorHandler elementWrapperHandler2 = LocatorProxies.getLocatorHandler(container.injected2.getElement());
        NanoHook elementWrapperHook2 = (NanoHook) elementWrapperHandler2.getInvocationTarget(null);

        assertThat(elementWrapperHook2.getBeforeClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook2.getAfterClickNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook2.getOptionValue()).isEqualTo("test");

        assertThat(elementWrapperHook2.getBeforeFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook2.getAfterFindElementNano()).isNotEqualTo(0L);
        assertThat(elementWrapperHook2.getBeforeFindElementsNano()).isEqualTo(0L);
        assertThat(elementWrapperHook2.getAfterFindElementsNano()).isEqualTo(0L);

        assertThat(container.injectedNoHook).isNotNull();

        container.injectedNoHook.getElement().click();
        verify(elementNoHook).click();

        LocatorHandler elementNoHookHandler = LocatorProxies.getLocatorHandler(container.injectedNoHook.getElement());
        WebElement elementNoHookLocatorResult = (WebElement) elementNoHookHandler.getInvocationTarget(null);

        assertThat(elementNoHookLocatorResult).isSameAs(elementNoHook);
    }

}
