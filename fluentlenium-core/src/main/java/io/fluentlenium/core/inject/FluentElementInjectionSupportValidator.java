package io.fluentlenium.core.inject;

import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.utils.CollectionUtils;
import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static io.fluentlenium.core.inject.InjectionAnnotationSupport.isNoInject;
import static io.fluentlenium.utils.CollectionUtils.isList;

/**
 * Provides method for validating whether the injection of a field into a container is supported,
 * and methods for validating if fields has certain types.
 */
final class FluentElementInjectionSupportValidator {

    private final ComponentsManager componentsManager;

    FluentElementInjectionSupportValidator(ComponentsManager componentsManager) {
        this.componentsManager = requireNonNull(componentsManager);
    }

    /**
     * Returns whether injection of the argument field is supported into the argument container.
     *
     * @param container the container object to inject into
     * @param field     the field to inject
     * @return true if injection is supported, false otherwise
     */
    boolean isSupported(Object container, Field field) {
        return !isInJdkModule(field)
                && isFieldExist(container, field)
                && !isNoInject(field)
                && !Modifier.isFinal(field.getModifiers())
                && (isListOfFluentWebElement(field)
                || isListOfComponent(field)
                || isComponent(field)
                || isComponentList(field)
                || isWebElement(field)
                || isListOfWebElement(field));
    }

    boolean isInJdkModule(Field field) {
        return field.getDeclaringClass().getName().startsWith("java.")
                || field.getDeclaringClass().getName().startsWith("jdk.");
    }

    private static boolean isFieldExist(Object container, Field field) {
        try {
            return ReflectionUtils.get(field, container) == null;
        } catch (IllegalAccessException e) {
            throw new FluentInjectException("Can't retrieve default value of field", e);
        }
    }

    /**
     * Checks whether the argument field is a list of components e.g. {@link FluentWebElement} or any other custom component:
     * {@code List<FluentWebElement> elements} or for a custom component {@code Component} it would be
     * {@code List<Component> components;}.
     *
     * @param field the field to check the type of
     * @return true if field is a list of components, false otherwise
     */
    boolean isListOfComponent(Field field) {
        //Don't replace the predicate with a method reference
        return isFieldListOf(field, genericType -> componentsManager.isComponentClass(genericType));
    }

    /**
     * Checks whether the argument field is a component e.g. {@link FluentWebElement} or any other custom component.
     *
     * @param field the field to check the type of
     * @return true if field is a component, false otherwise
     */
    boolean isComponent(Field field) {
        return componentsManager.isComponentClass(field.getType());
    }

    /**
     * Checks whether the argument field is a type that extends {@link List} and its generic type is a component
     * e.g. {@link FluentWebElement} or any other custom component.
     *
     * @param field the field to check the type of
     * @return true if field is a list of components, false otherwise
     */
    boolean isComponentList(Field field) {
        if (CollectionUtils.isList(field)) {
            boolean componentListClass = componentsManager.isComponentListClass((Class<? extends List<?>>) field.getType());
            if (componentListClass) {
                Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
                return genericType != null && componentsManager.isComponentClass(genericType);
            }
        }
        return false;
    }

    /**
     * Checks whether the argument field is a list of {@link FluentWebElement}s: {@code List<FluentWebElement> elements;}
     *
     * @param field the field to check the type of
     * @return true if field is a list of FluentWebElements, false otherwise
     */
    static boolean isListOfFluentWebElement(Field field) {
        return isFieldListOf(field, FluentWebElement.class::isAssignableFrom);
    }

    /**
     * Checks whether the argument field is a {@link WebElement}.
     *
     * @param field the field to check the type of
     * @return true if field is WebElement, false otherwise
     */
    static boolean isWebElement(Field field) {
        return WebElement.class.isAssignableFrom(field.getType());
    }

    /**
     * Checks whether the argument field is a list of {@link WebElement}s: {@code List<WebElement> elements;}
     *
     * @param field the field to check the type of
     * @return true if field is a list of WebElements, false otherwise
     */
    static boolean isListOfWebElement(Field field) {
        return isFieldListOf(field, WebElement.class::isAssignableFrom);
    }

    private static boolean isFieldListOf(Field field, Predicate<Class<?>> typePredicate) {
        if (CollectionUtils.isList(field)) {
            Class<?> genericType = ReflectionUtils.getFirstGenericType(field);
            return genericType != null && typePredicate.test(genericType);
        }
        return false;
    }
}
