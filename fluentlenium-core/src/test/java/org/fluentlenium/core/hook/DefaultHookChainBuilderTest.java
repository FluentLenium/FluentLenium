package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHookChainBuilderTest {
    @Mock
    private WebElement element;

    @Mock
    private ElementLocator locator;

    @Mock
    private WebDriver webDriver;

    private ComponentInstantiator instantiator;

    private HookChainBuilder hookChainBuilder;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
        hookChainBuilder = new DefaultHookChainBuilder(fluentAdapter, instantiator) {
            @Override
            protected FluentHook<?> newInstance(Class<? extends FluentHook<?>> hookClass, FluentControl fluentControl,
                    ComponentInstantiator instantiator, Supplier<WebElement> currentSupplier, Supplier<ElementLocator> locator,
                    Supplier<String> toStringSupplier, Object options)
                    throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                return spy(super.newInstance(hookClass, fluentControl, instantiator, currentSupplier, locator, toStringSupplier,
                        options));
            }
        };
    }

    @Test
    public void testBuildHook() {
        List<HookDefinition<?>> hookDefinitions = new ArrayList<>();

        hookDefinitions.add(new HookDefinition<>(NanoHook.class));
        hookDefinitions.add(new HookDefinition<>(NanoHook.class, new NanoHookOptions("option")));
        hookDefinitions.add(new HookDefinition<>(NanoHook.class));

        List<FluentHook> fluentHooks = hookChainBuilder
                .build(Suppliers.ofInstance(element), Suppliers.ofInstance(locator), Suppliers.ofInstance("toString"),
                        hookDefinitions);

        Assertions.assertThat(fluentHooks).hasSize(hookDefinitions.size());

        Assertions.assertThat(fluentHooks.get(0)).isInstanceOf(NanoHook.class);
        Assertions.assertThat(fluentHooks.get(1)).isInstanceOf(NanoHook.class);
        Assertions.assertThat(fluentHooks.get(2)).isInstanceOf(NanoHook.class);

        fluentHooks.get(0).click();

        verify(element).click();
        verify(fluentHooks.get(0)).click();
        verify(fluentHooks.get(1), never()).click();
        verify(fluentHooks.get(2), never()).click();

        reset(element);
        reset(fluentHooks.toArray());

        fluentHooks.get(2).click();

        verify(element).click();
        verify(fluentHooks.get(0)).click();
        verify(fluentHooks.get(1)).click();
        verify(fluentHooks.get(2)).click();

        Assertions.assertThat(((NanoHook) fluentHooks.get(2)).getBeforeClickNano())
                .isLessThanOrEqualTo(((NanoHook) fluentHooks.get(1)).getBeforeClickNano());
        Assertions.assertThat(((NanoHook) fluentHooks.get(1)).getBeforeClickNano())
                .isLessThanOrEqualTo(((NanoHook) fluentHooks.get(0)).getBeforeClickNano());
        Assertions.assertThat(((NanoHook) fluentHooks.get(2)).getAfterClickNano())
                .isGreaterThanOrEqualTo(((NanoHook) fluentHooks.get(1)).getAfterClickNano());
        Assertions.assertThat(((NanoHook) fluentHooks.get(1)).getAfterClickNano())
                .isGreaterThanOrEqualTo(((NanoHook) fluentHooks.get(0)).getAfterClickNano());

        Assertions.assertThat(((NanoHook) fluentHooks.get(0)).getOptionValue()).isNull();
        Assertions.assertThat(((NanoHook) fluentHooks.get(1)).getOptionValue()).isEqualTo("option");
        Assertions.assertThat(((NanoHook) fluentHooks.get(2)).getOptionValue()).isNull();
    }

    private static class InvalidConstructorHook extends BaseHook<Object> {
        InvalidConstructorHook() {
            super(null, null, null, null, null, null);
        }
    }

    @Test
    public void testInvalidConstructorHook() {
        final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();

        hookDefinitions.add(new HookDefinition<>(InvalidConstructorHook.class));

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                hookChainBuilder
                        .build(Suppliers.ofInstance(element), Suppliers.ofInstance(locator), Suppliers.ofInstance("toString"),
                                hookDefinitions);
            }
        }).isExactlyInstanceOf(HookException.class).hasMessage("An error has occurred with a defined hook.");

    }

    private static class FailingConstructorHook extends BaseHook<Object> {
        FailingConstructorHook(FluentControl fluentControl, ComponentInstantiator instantiator,
                Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier,
                Object options) {
            super(fluentControl, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
            throw new IllegalStateException();
        }
    }

    @Test
    public void testFailingConstructorHook() {
        final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();

        hookDefinitions.add(new HookDefinition<>(FailingConstructorHook.class));

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                hookChainBuilder
                        .build(Suppliers.ofInstance(element), Suppliers.ofInstance(locator), Suppliers.ofInstance("toString"),
                                hookDefinitions);
            }
        }).isExactlyInstanceOf(HookException.class).hasMessage("An error has occurred with a defined hook.");

    }
}
