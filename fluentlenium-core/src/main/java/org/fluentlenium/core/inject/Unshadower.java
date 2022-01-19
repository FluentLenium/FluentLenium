package org.fluentlenium.core.inject;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;
import java.util.*;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
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
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Unshadow.class)) {
                unshadowField(field);
            }
        }
    }

    private void unshadowField(Field field) {
        String[] cssSelectors = field.getAnnotation(Unshadow.class).css();

        List<List<WebElement>> deepestShadowRoots = extractShadowRoots(cssSelectors);

        setValue(field, convertToFluentWebElementList(deepestShadowRoots.get(deepestShadowRoots.size() - 1)));
    }

    private List<FluentWebElement> convertToFluentWebElementList(List<WebElement> lastShadowRoots) {
        List<FluentWebElement> list = new ArrayList<>();
        for (WebElement element : lastShadowRoots) {
            FluentWebElement fluentWebElement = new FluentWebElement(element, page.getFluentControl(), page.getFluentControl());
            list.add(fluentWebElement);
        }
        return list;
    }

    private List<List<WebElement>> extractShadowRoots(String[] cssSelectors) {
        if (cssSelectors.length % 2 != 0) {
            LOGGER.error("CSS selector count needs to be even number");
            return null;
        }

        WebElement domRoot = webDriver.findElement(By.xpath("/*"));

        List<WebElement> acc1 = singletonList(domRoot);

        List<List<WebElement>> acc = new ArrayList<>();
        acc.add(acc1);

        for (int i = 0; cssSelectors.length > i; i += 2) {
            acc = extractElementsFromShadowRoot(acc, cssSelectors[i], cssSelectors[i + 1]);
        }
        return acc;
    }

    private List<List<WebElement>> extractElementsFromShadowRoot(List<List<WebElement>> previousNodes, String cssSelector, String cssSelector1) {
        List<List<WebElement>> list = new ArrayList<>();

        for (List<WebElement> previousNodesElements : previousNodes) {
            for (WebElement nodeElements : previousNodesElements) {
                for (WebElement webElement : nodeElements.findElements(By.cssSelector(cssSelector))) {
                    List<WebElement> unshadow = unshadow(webElement, cssSelector1);
                    list.add(unshadow);
                }
            }
        }
        return list;
    }

    private List<WebElement> unshadow(WebElement element, String cssSelector) {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        SearchContext shadowRoot = (SearchContext) executor.executeScript("return arguments[0].shadowRoot", element);

        return shadowRoot.findElements(By.cssSelector(cssSelector));
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

