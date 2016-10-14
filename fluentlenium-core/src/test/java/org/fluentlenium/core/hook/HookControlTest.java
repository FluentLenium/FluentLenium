package org.fluentlenium.core.hook;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HookControlTest {
    private final Object proxy = new Object();

    @Mock
    private FluentControl control;

    @Mock
    private Supplier<HookControl> supplier;

    private final ComponentInstantiator instantiator = new DefaultComponentInstantiator(control);

    private HookControlImpl<HookControl> hookControl;

    public static class HookDefinitionMatcher extends ArgumentMatcher<List<HookDefinition<?>>> {
        private final Class<?>[] hooks;

        public HookDefinitionMatcher(final Class<?>[] hooks) {
            this.hooks = hooks;
        }

        @Override
        public boolean matches(final Object argument) {
            final List<HookDefinition<?>> hookDefinitions = (List<HookDefinition<?>>) argument;

            if (hookDefinitions.size() != hooks.length) {
                return false;
            }

            for (int i = 0; i < hookDefinitions.size(); i++) {
                if (!hookDefinitions.get(i).getHookClass().equals(hooks[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static List<HookDefinition<?>> hookDefinition(final Class<?>... hooks) {
        return argThat(new HookDefinitionMatcher(hooks));
    }

    private static class Hook1 extends BaseHook {
        Hook1(final FluentControl control, final ComponentInstantiator instantiator, final Supplier supplier,
                final Supplier supplier2, final Supplier toStringSupplier, final Object options) {
            super(control, instantiator, supplier, supplier2, toStringSupplier, options);
        }
    }

    private static class Hook2 extends BaseHook {
        Hook2(final FluentControl control, final ComponentInstantiator instantiator, final Supplier supplier,
                final Supplier supplier2, final Supplier toStringSupplier, final Object options) {
            super(control, instantiator, supplier, supplier2, toStringSupplier, options);
        }
    }

    private static class Hook3 extends BaseHook {
        Hook3(final FluentControl control, final ComponentInstantiator instantiator, final Supplier supplier,
                final Supplier supplier2, final Supplier toStringSupplier, final Object options) {
            super(control, instantiator, supplier, supplier2, toStringSupplier, options);
        }
    }

    public void resetAndMock(final HookControlImpl<?> hookControl) {
        reset(hookControl);
        doNothing().when(hookControl).applyHooks(any(Object.class), any(HookChainBuilder.class), anyList());
    }

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        hookControl = spy(new HookControlImpl<>(null, proxy, control, instantiator, supplier));
        ReflectionUtils.set(HookControlImpl.class.getDeclaredField("self"), hookControl, hookControl);
        when(supplier.get()).thenAnswer(new Answer<HookControlImpl>() {
            @Override
            public HookControlImpl answer(final InvocationOnMock invocation) throws Throwable {
                final HookControlImpl<HookControl> answer = spy(
                        new HookControlImpl<>(null, proxy, control, instantiator, supplier));
                ReflectionUtils.set(HookControlImpl.class.getDeclaredField("self"), answer, answer);
                resetAndMock(answer);
                return answer;
            }
        });
        resetAndMock(hookControl);
    }

    @Test
    public void testHook() {
        hookControl.withHook(Hook1.class);

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class));
        resetAndMock(this.hookControl);
    }

    @Test
    public void testNoHook() {
        this.hookControl.withHook(Hook1.class);

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class));
        resetAndMock(this.hookControl);

        hookControl.noHook();

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition());
        resetAndMock(this.hookControl);
    }

    @Test
    public void testNoHookInstance() {
        this.hookControl.withHook(Hook1.class);

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class));
        resetAndMock(this.hookControl);

        final HookControlImpl newInstance = (HookControlImpl) hookControl.noHookInstance();
        assertThat(newInstance.getHookDefinitions()).isEmpty();
        assertThat(this.hookControl.getHookDefinitions()).hasSize(1);
    }

    @Test
    public void testNoHookOneClassInstance() {
        this.hookControl.withHook(Hook1.class);

        resetAndMock(this.hookControl);
        this.hookControl.withHook(Hook2.class);

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class));
        resetAndMock(this.hookControl);

        final HookControlImpl newInstance = (HookControlImpl) hookControl.noHookInstance(Hook1.class);

        assertThat(newInstance.getHookDefinitions()).hasSize(1);
        assertThat(this.hookControl.getHookDefinitions()).hasSize(2);
    }

    @Test
    public void testNoHookOneClass() {
        this.hookControl.withHook(Hook1.class);
        resetAndMock(this.hookControl);

        this.hookControl.withHook(Hook2.class);

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class));
        resetAndMock(this.hookControl);

        hookControl.noHook(Hook2.class);

        verify(this.hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class));
        resetAndMock(this.hookControl);
    }

    @Test
    public void testThreeHooksNoHookAndRestore() {
        hookControl.withHook(Hook1.class);
        hookControl.withHook(Hook2.class);

        resetAndMock(this.hookControl);
        hookControl.withHook(Hook3.class);

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class, Hook3.class));
        resetAndMock(this.hookControl);

        hookControl.noHook();

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition());
        resetAndMock(this.hookControl);

        hookControl.restoreHooks();

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class, Hook3.class));
        resetAndMock(this.hookControl);
    }

    @Test
    public void testHooksNoHookFunction() {
        hookControl.withHook(Hook1.class);
        hookControl.withHook(Hook2.class);
        resetAndMock(hookControl);

        assertThat(hookControl.noHook(new Function<HookControl, String>() {
            @Override
            public String apply(final HookControl input) {
                assertThat(input).isSameAs(hookControl);
                assertThat(hookControl.getHookDefinitions()).isEmpty();
                verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition());
                resetAndMock(hookControl);
                return "test";
            }
        })).isEqualTo("test");

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class));

        resetAndMock(hookControl);
        hookControl.withHook(Hook3.class);

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class, Hook3.class));
        resetAndMock(this.hookControl);
    }

    @Test
    public void testHooksNoHookOneClassFunction() {
        hookControl.withHook(Hook1.class);
        hookControl.withHook(Hook2.class);
        resetAndMock(hookControl);

        assertThat(hookControl.noHook(Hook1.class, new Function<HookControl, String>() {
            @Override
            public String apply(final HookControl input) {
                assertThat(input).isSameAs(hookControl);
                assertThat(hookControl.getHookDefinitions()).hasSize(1);
                assertThat(hookControl.getHookDefinitions().get(0).getHookClass()).isEqualTo(Hook2.class);
                verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook2.class));
                resetAndMock(hookControl);
                return "test";
            }
        })).isEqualTo("test");

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class));

        resetAndMock(hookControl);
        hookControl.withHook(Hook3.class);

        verify(hookControl).applyHooks(eq(proxy), any(), hookDefinition(Hook1.class, Hook2.class, Hook3.class));
        resetAndMock(this.hookControl);
    }

}
