package org.fluentlenium.core.inject;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unshadower {
    private static final Logger LOGGER = LoggerFactory.getLogger(Unshadower.class);

    private final WebDriver webDriver;
    private final FluentPage page;

    public Unshadower(WebDriver webDriver, FluentPage page) {
        this.webDriver = webDriver;
        this.page = page;
    }

    public void unshadowAllAnnotatedFields() {
        Arrays.stream(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Unshadow.class))
                .forEach(this::unshadowField);
    }

    private void unshadowField(Field field) {
        String[] cssSelectors = field.getAnnotation(Unshadow.class).css();
        List<List<WebElement>> deepestShadowRoots = extractShadowRoots(cssSelectors);

        setValue(field, convertToFluentWebElementList(deepestShadowRoots.get(deepestShadowRoots.size() - 1)));
    }

    private List<FluentWebElement> convertToFluentWebElementList(List<WebElement> lastShadowRoots) {
        return lastShadowRoots.stream()
                .map(element -> new FluentWebElement(element, page.getFluentControl(), page.getFluentControl()))
                .collect(toList());
    }

    private List<List<WebElement>> extractShadowRoots(String[] cssSelectors) {
        WebElement domRoot = webDriver.findElement(By.xpath("/*"));
        List<List<WebElement>> currentShadowRootsInContext = singletonList(singletonList(domRoot));

        if (cssSelectors.length == 1) {
            currentShadowRootsInContext = extractElementsFromShadowRoot(currentShadowRootsInContext, cssSelectors[0], By.xpath("/*"));
        } else {
            for (int i = 0; cssSelectors.length - 1 > i; i++) {
                currentShadowRootsInContext = extractElementsFromShadowRoot(currentShadowRootsInContext, cssSelectors[i], By.cssSelector(cssSelectors[i + 1]));
            }
        }
        return currentShadowRootsInContext;
    }

    private List<List<WebElement>> extractElementsFromShadowRoot(List<List<WebElement>> previousNodes,
                                                                 String cssSelector, By selector) {
        return previousNodes.stream()
                .flatMap(Collection::stream)
                .flatMap(webElement -> webElement.findElements(By.cssSelector(cssSelector)).stream())
                .map(element -> unshadow(element, selector))
                .collect(toList());
    }

    private List<WebElement> unshadow(WebElement element, By selector) {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        SearchContext shadowRoot = (SearchContext) executor.executeScript("return arguments[0].shadowRoot", element);
        return shadowRoot.findElements(selector);
    }

    private void setValue(Field field, List<FluentWebElement> elements) {
        if (List.class.isAssignableFrom(field.getType())) {
            setValueToField(field, elements);
        } else if (Set.class.isAssignableFrom(field.getType())) {
            setValueToField(field, ImmutableSet.copyOf(elements));
        } else if (!elements.isEmpty()) {
            setValueToField(field, elements.get(0));
        }
    }

    private void setValueToField(Field field, Object value) {
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(page, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("Couldn't set value to field", e);
        } finally {
            field.setAccessible(isAccessible);
        }
    }

}

