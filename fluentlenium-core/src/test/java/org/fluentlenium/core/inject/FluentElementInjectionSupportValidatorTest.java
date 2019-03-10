package org.fluentlenium.core.inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Unit test for {@link FluentElementInjectionSupportValidator}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentElementInjectionSupportValidatorTest {

    @Mock
    private ComponentsManager componentsManager;
    private FluentElementInjectionSupportValidator validator;
    private TestPage testPage;

    @Before
    public void init() {
        validator = new FluentElementInjectionSupportValidator(componentsManager);
        testPage = new TestPage();
    }

    //isListOfComponent

    @Test
    public void shouldReturnFalseWhenFieldIsNotList() throws NoSuchFieldException {
        Field webElementField = getField("webElement");

        assertThat(validator.isListOfComponent(webElementField)).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenFieldIsNotListOfComponent() throws NoSuchFieldException {
        when(componentsManager.isComponentClass(WebElement.class)).thenReturn(false);
        Field listOfWebElementsField = getField("listOfWebElements");

        assertThat(validator.isListOfComponent(listOfWebElementsField)).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenFieldIsListOfComponent() throws NoSuchFieldException {
        when(componentsManager.isComponentClass(TestComponent.class)).thenReturn(true);
        Field listOfComponentsField = getField("listOfComponents");

        assertThat(validator.isListOfComponent(listOfComponentsField)).isTrue();
    }

    //isComponent

    @Test
    public void isComponent() throws NoSuchFieldException {
        when(componentsManager.isComponentClass(TestComponent.class)).thenReturn(true);
        Field componentField = getField("component");

        assertThat(validator.isComponent(componentField)).isTrue();
    }

    //isComponentList

    @Test
    public void shouldReturnFalseIfFieldIsNotListForComponentList() throws NoSuchFieldException {
        Field notListField = getField("webElement");

        assertThat(validator.isComponentList(notListField)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfFieldIsNotComponentList() throws NoSuchFieldException {
        Field listOfFluentWebElementsField = getField("listOfFluentWebElements");
        when(componentsManager.isComponentListClass((Class<? extends List<?>>) listOfFluentWebElementsField.getType()))
                .thenReturn(false);

        assertThat(validator.isComponentList(listOfFluentWebElementsField)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfFieldGenericTypeIsNotComponent() throws NoSuchFieldException {
        Field componentFluentWebElementListField = getField("componentFluentWebElementList");
        when(componentsManager.isComponentListClass((Class<? extends List<?>>) componentFluentWebElementListField.getType()))
                .thenReturn(true);
        when(componentsManager.isComponentClass(FluentWebElement.class)).thenReturn(false);

        assertThat(validator.isComponentList(componentFluentWebElementListField)).isFalse();
    }

    @Test
    public void shouldReturnTrueIfFieldIsComponentList() throws NoSuchFieldException {
        Field componentListField = getField("componentList");
        when(componentsManager.isComponentListClass((Class<? extends List<?>>) componentListField.getType())).thenReturn(true);
        when(componentsManager.isComponentClass(TestComponent.class)).thenReturn(true);

        assertThat(validator.isComponentList(componentListField)).isTrue();
    }

    //isListOfFluentWebElement

    @Test
    public void shouldReturnFalseIfFieldIsNotListForListOfFluentWebElement() throws NoSuchFieldException {
        Field notListField = getField("webElement");

        assertThat(FluentElementInjectionSupportValidator.isListOfFluentWebElement(notListField)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfFieldIsNotListOfFluentWebElement() throws NoSuchFieldException {
        Field listOfNotFluentWebElementField = getField("listOfWebElements");

        assertThat(FluentElementInjectionSupportValidator.isListOfFluentWebElement(listOfNotFluentWebElementField)).isFalse();
    }

    @Test
    public void shouldReturnTrueIfFieldIsListOfFluentWebElement() throws NoSuchFieldException {
        Field listOfFluentWebElementField = getField("listOfFluentWebElements");

        assertThat(FluentElementInjectionSupportValidator.isListOfFluentWebElement(listOfFluentWebElementField)).isTrue();
    }

    //isElement

    @Test
    public void shouldReturnTrueIfFieldIsWebElement() throws NoSuchFieldException {
        Field webElementField = getField("webElement");

        assertThat(FluentElementInjectionSupportValidator.isElement(webElementField)).isTrue();
    }

    @Test
    public void shouldReturnFalseIfFieldIsNotWebElement() throws NoSuchFieldException {
        Field fluentWebElementField = getField("fluentWebElement");

        assertThat(FluentElementInjectionSupportValidator.isElement(fluentWebElementField)).isFalse();
    }

    //isListOfElement

    @Test
    public void shouldReturnFalseIfFieldIsNotListForListOfElement() throws NoSuchFieldException {
        Field notListField = getField("webElement");

        assertThat(FluentElementInjectionSupportValidator.isListOfElement(notListField)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfFieldIsNotListOfElement() throws NoSuchFieldException {
        Field listOfNotFluentWebElementField = getField("listOfFluentWebElements");

        assertThat(FluentElementInjectionSupportValidator.isListOfElement(listOfNotFluentWebElementField)).isFalse();
    }

    @Test
    public void shouldReturnTrueIfFieldIsListOfElement() throws NoSuchFieldException {
        Field listOfFluentWebElementField = getField("listOfWebElements");

        assertThat(FluentElementInjectionSupportValidator.isListOfElement(listOfFluentWebElementField)).isTrue();
    }

    //Support methods and classes

    private Field getField(String fieldName) throws NoSuchFieldException {
        return testPage.getClass().getDeclaredField(fieldName);
    }

    private static final class TestPage {
        List<TestComponent> listOfComponents;

        TestComponent component;

        WebElement webElement;

        List<WebElement> listOfWebElements;

        FluentWebElement fluentWebElement;

        List<FluentWebElement> listOfFluentWebElements;

        ComponentList<FluentWebElement> componentFluentWebElementList;

        ComponentList<TestComponent> componentList;
    }

    private static final class TestComponent extends FluentWebElement {
        public TestComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    public static class ComponentList<T extends FluentWebElement> extends FluentListImpl<T> {
        public ComponentList(Class<T> componentClass, List<T> list, FluentControl fluentControl,
                             ComponentInstantiator instantiator) {
            super(componentClass, list, fluentControl, instantiator);
        }
    }
}
