package io.fluentlenium.test.annotations;

import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.events.annotations.BeforeClickOn;import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.events.annotations.BeforeClickOn;
import io.fluentlenium.test.IntegrationFluentTest;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

class AnnotationsComponentEventsTestSubClass extends IntegrationFluentTest {

    List<WebElement> afterClick = new ArrayList<>();

    @BeforeClickOn
    private void afterClickOn(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        afterClick.add(element.getElement());
    }
}
