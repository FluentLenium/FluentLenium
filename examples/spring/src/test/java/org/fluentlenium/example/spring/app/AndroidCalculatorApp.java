package org.fluentlenium.example.spring.app;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.spring.ExampleFluentTest;
import org.junit.Test;

public class AndroidCalculatorApp extends ExampleFluentTest {

    @Page
    private AndroidCalculator calculator;

    @Test
    public void shouldCorrectlyAdd() {
        calculator.addTwoPlusFour().verifyResult(6);
    }

}

