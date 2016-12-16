package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsPriorityEventsTest extends IntegrationFluentTest {
    private static List<Integer> beforeClick = new ArrayList<>();

    @Test
    public void clickOn() {
        goTo(DEFAULT_URL);
        $("button").click();
        assertThat(beforeClick).containsExactly(5, 2, 1, 0, -2);
    }

    @BeforeClickOn
    private void beforeClickOn(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        beforeClick.add(0);
    }

    @BeforeClickOn(2)
    private void beforeClickOn2(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        beforeClick.add(2);
    }

    @BeforeClickOn(1)
    private void beforeClickOn1(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        beforeClick.add(1);
    }

    @BeforeClickOn(5)
    private void beforeClickOn5(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        beforeClick.add(5);
    }

    @BeforeClickOn(-2)
    private void beforeClickOnNeg2(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        beforeClick.add(-2);
    }

}
