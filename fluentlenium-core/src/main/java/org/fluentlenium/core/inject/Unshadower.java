package org.fluentlenium.core.inject;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        Arrays.stream(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Unshadow.class))
                .forEach(this::unshadowField);
    }

    private void unshadowField(Field field) {
        String[] cssSelectors = field.getAnnotation(Unshadow.class).css();

        List<WebElement> deepestShadowRoots = extractShadowRoots(cssSelectors);
        List<FluentWebElement> elements = findElementsInLastShadowRoot(cssSelectors[cssSelectors.length - 1], deepestShadowRoots);

        setValue(field, elements);
    }

    private List<FluentWebElement> findElementsInLastShadowRoot(String cssSelector, List<WebElement> lastShadowRoots) {
        return lastShadowRoots.stream()
                .flatMap(shadowRoot -> shadowRoot.findElements(By.cssSelector(cssSelector)).stream())
                .filter(Objects::nonNull)
                .map(element -> new FluentWebElement(element, page.getFluentControl(), page.getFluentControl()))
                .collect(toList());
    }

    private List<WebElement> extractShadowRoots(String[] cssSelectors) {
        WebElement domRoot = webDriver.findElement(By.xpath("/*"));
        return Arrays.stream(cssSelectors)
                .limit(cssSelectors.length - 1)
                .reduce(singletonList(domRoot), this::extractElementsFromShadowRoot, (acc, val) -> val);
    }

    private List<WebElement> extractElementsFromShadowRoot(List<WebElement> previousNodes, String cssSelector) {
        return previousNodes.stream()
                .flatMap(node -> node.findElements(By.cssSelector(cssSelector)).stream())
                .map(this::unshadow)
                .collect(toList());
    }

    private WebElement unshadow(WebElement elements) {
        WebElement returnObj = null;

        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        Object shadowRoot = executor.executeScript("return arguments[0].shadowRoot", elements);

        if (shadowRoot instanceof WebElement) {
            // ChromeDriver 95
            returnObj = (WebElement) shadowRoot;
        } else if (shadowRoot instanceof Map) {
            // ChromeDriver 96+
            // Based on https://github.com/SeleniumHQ/selenium/issues/10050#issuecomment-974231601
            Map<String, Object> shadowRootMap = (Map<String, Object>) shadowRoot;
            String shadowRootKey = (String) shadowRootMap.keySet().toArray()[0];
            String id = (String) shadowRootMap.get(shadowRootKey);
            RemoteWebElement remoteWebElement = new RemoteWebElement();
            remoteWebElement.setParent((RemoteWebDriver) webDriver);
            remoteWebElement.setId(id);
            returnObj = remoteWebElement;
        } else {
            LOGGER.error("Unexpected return type for shadowRoot in expandRootElement()");
        }
        return returnObj;
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

