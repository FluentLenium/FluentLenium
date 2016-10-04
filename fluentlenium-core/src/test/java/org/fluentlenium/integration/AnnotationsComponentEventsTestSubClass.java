package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AnnotationsComponentEventsTestSubClass extends IntegrationFluentTest {
    protected List<WebElement> afterClick = new ArrayList<>();

    @BeforeClickOn
    private void afterClickOn(final FluentWebElement element) { // NOPMD UnusedPrivateMethod
        afterClick.add(element.getElement());
    }
}
