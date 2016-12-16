package org.fluentlenium.core.inject;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentsManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

@RunWith(MockitoJUnitRunner.class)
public class FluentInjectorContainerTest {

    @Mock
    private WebDriver webDriver;

    private FluentAdapter fluentAdapter;

    private FluentInjector injector;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        injector = new FluentInjector(fluentAdapter, null, new ComponentsManager(fluentAdapter),
                new DefaultContainerInstanciator(fluentAdapter));
    }

    public static class ChildContainer {

    }

    public static class ParentContainer {
        @Page
        private ChildContainer childContainer;
    }

    @Test
    public void testInjectChildContainer() {
        ParentContainer parentContainer = new ParentContainer();

        Assertions.assertThat(parentContainer.childContainer).isNull();

        injector.inject(parentContainer);

        Assertions.assertThat(parentContainer.childContainer).isExactlyInstanceOf(ChildContainer.class);
    }

    public static class ChildContainerRecurse {
        @Page
        private ParentContainerRecurse parentContainer;
    }

    public static class ParentContainerRecurse {
        @Page
        private ChildContainerRecurse childContainer;
    }

    @Test
    public void testInjectRecursiveContainers() {
        ParentContainerRecurse parentContainer = new ParentContainerRecurse();

        Assertions.assertThat(parentContainer.childContainer).isNull();

        injector.inject(parentContainer);

        Assertions.assertThat(parentContainer.childContainer).isExactlyInstanceOf(ChildContainerRecurse.class);
        Assertions.assertThat(parentContainer.childContainer.parentContainer).isExactlyInstanceOf(ParentContainerRecurse.class);
    }

    public static class ChildContainerInit implements FluentContainer {
        @Page
        private ParentContainerRecurse parentContainer;

        private FluentControl control;

        @Override
        public void initFluent(FluentControl control) {
            this.control = control;
        }
    }

    public static class ParentContainerInit implements FluentContainer {
        private FluentControl control;

        @Page
        private ChildContainerInit childContainer;

        @Override
        public void initFluent(FluentControl control) {
            this.control = control;
        }
    }

    @Test
    public void testInjectInitialise() {
        ParentContainerInit parentContainer = new ParentContainerInit();

        Assertions.assertThat(parentContainer.childContainer).isNull();

        injector.inject(parentContainer);

        Assertions.assertThat(parentContainer.control).isInstanceOf(ContainerFluentControl.class);

        Assertions.assertThat(((ContainerFluentControl) parentContainer.control).getAdapterControl()).isSameAs(fluentAdapter);
        Assertions.assertThat(parentContainer.childContainer.control).isInstanceOf(ContainerFluentControl.class);

        Assertions.assertThat(((ContainerFluentControl) parentContainer.childContainer.control).getAdapterControl())
                .isSameAs(fluentAdapter);
    }

    public static class ChildContainerConstructorInit {
        @Page
        private ParentContainerConstructorInit parentContainer;

        private final FluentControl control;

        public ChildContainerConstructorInit(FluentControl control) {
            this.control = control;
        }
    }

    public static class ParentContainerConstructorInit {
        private final FluentControl control;

        @Page
        private ChildContainerConstructorInit childContainer;

        public ParentContainerConstructorInit(FluentControl control) {
            this.control = control;
        }
    }

    @Test
    public void testInjectConstructorInitialise() {
        ParentContainerConstructorInit parentContainer = new ParentContainerConstructorInit(fluentAdapter);

        Assertions.assertThat(parentContainer.childContainer).isNull();

        injector.inject(parentContainer);

        Assertions.assertThat(parentContainer.control).isSameAs(fluentAdapter);
        Assertions.assertThat(parentContainer.childContainer.control).isInstanceOf(ContainerFluentControl.class);

        Assertions.assertThat(((ContainerFluentControl) parentContainer.childContainer.control).getAdapterControl())
                .isSameAs(fluentAdapter);

    }

}
