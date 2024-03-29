package io.fluentlenium.adapter.cucumber.unit;

import cucumber.runtime.java.fluentlenium.FluentObjectFactory;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentObjectFactoryTest {

    private FluentObjectFactory objectFactory;

    @Before
    public void before() {
        objectFactory = new FluentObjectFactory();
        objectFactory.start();
    }

    @After
    public void after() {
        objectFactory.stop();
    }

    @Test
    public void testParentContainerGetInstanceFactory() {
        ParentContainer parentContainer = objectFactory.getInstance(ParentContainer.class);

        assertThat(parentContainer).isInstanceOf(ParentContainer.class);
    }

    @Test
    public void testChildContainerGetInstanceFactory() {
        ParentContainer parentContainer = objectFactory.getInstance(ParentContainer.class);

        assertThat(parentContainer.childContainer).isExactlyInstanceOf(ChildContainer.class);
    }

    @Test
    public void testElementInjectionInChildContainer() {
        ParentContainer parentContainer = objectFactory.getInstance(ParentContainer.class);

        Assertions.assertThat(parentContainer.childContainer.element).isExactlyInstanceOf(FluentWebElement.class);
    }

    @Test
    public void testDoubleGetInstanceGiveSameInstance() {
        ParentContainer parentContainer1 = objectFactory.getInstance(ParentContainer.class);
        ParentContainer parentContainer2 = objectFactory.getInstance(ParentContainer.class);

        assertThat(parentContainer1)
                .isInstanceOf(ParentContainer.class)
                .isEqualTo(parentContainer2);
    }

    @Test
    public void testRecursiveInjection() {
        ParentContainer parentContainer = objectFactory.getInstance(ParentContainer.class);

        assertThat(parentContainer.childContainer.parentContainer).isInstanceOf(ParentContainer.class);
    }

    @Test
    public void testInheritedChildContainer() {
        ParentContainerInherited container = objectFactory.getInstance(ParentContainerInherited.class);

        assertThat(container.childContainer.element).isInstanceOf(FluentWebElement.class);
    }

    public static class ChildContainer {
        @FindBy
        FluentWebElement element;
        @Page
        protected ParentContainer parentContainer;
    }

    public static class ParentContainer {
        @Page
        protected ChildContainer childContainer;
    }

    public static class ParentContainerInherited extends ParentContainer {
    }
}
