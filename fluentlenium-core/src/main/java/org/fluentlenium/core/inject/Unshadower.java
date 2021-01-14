package org.fluentlenium.core.inject;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Unshadow;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.util.ReflectionUtils;


public class Unshadower {
  private final WebDriver webDriver;
  private final FluentPage page;

  public Unshadower(WebDriver webDriver, FluentPage page) {
    this.webDriver = webDriver;
    this.page = page;
  }

  public void unshadowAllAnnotatedFields() {
    Arrays.stream(page.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Unshadow.class))
        .forEach(this::unshadowField);
  }

  private void unshadowField(Field field) {
    String[] cssSelectors = field.getAnnotation(Unshadow.class).css();

    var deepestShadowRoots = extractShadowRoots(cssSelectors);
    var elements = findElementsInLastShadowRoot(cssSelectors[cssSelectors.length - 1], deepestShadowRoots);

    setValue(field, elements);
  }

  private List<FluentWebElement> findElementsInLastShadowRoot(String cssSelector, List<WebElement> lastShadowRoots) {
    return lastShadowRoots.stream()
        .flatMap(we -> we.findElements(By.cssSelector(cssSelector)).stream())
        .filter(Objects::nonNull)
        .map(we -> new FluentWebElement(we, page.getFluentControl(), page.getFluentControl()))
        .collect(toList());
  }

  private List<WebElement> extractShadowRoots(String[] cssSelectors) {
    WebElement domRoot = webDriver.findElement(By.xpath("/*"));
    return Arrays.stream(cssSelectors)
        .limit(cssSelectors.length - 1)
        .reduce(List.of(domRoot), this::extractElementsFromShadowRoot, (l1, l2) -> l2);
  }

  private List<WebElement> extractElementsFromShadowRoot(List<WebElement> previousNodes, String cssSelector) {
    return previousNodes.stream()
        .flatMap(n -> n.findElements(By.cssSelector(cssSelector)).stream())
        .map(this::unshadow)
        .collect(toList());
  }

  private WebElement unshadow(WebElement elements) {
    var executor = (JavascriptExecutor) webDriver;
    return (WebElement) executor.executeScript("return arguments[0].shadowRoot", elements);
  }

  private void setValue(Field field, List<FluentWebElement> elements) {
    if (List.class.isAssignableFrom(field.getType())) {
      ReflectionUtils.setField(field, page, elements);
    } else if (Set.class.isAssignableFrom(field.getType())) {
      ReflectionUtils.setField(field, page, Set.copyOf(elements));
    } else if (!elements.isEmpty()) {
      ReflectionUtils.setField(field, page, elements.get(0));
    }
  }

}

