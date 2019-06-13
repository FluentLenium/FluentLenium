package org.fluentlenium.example.spring;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.spring.app.IosTestApp;
import org.junit.Test;

public class AppiumIosApp extends ExampleFluentTest {

    @Page
    private IosTestApp iosTestApp;

    @Test
    public void shouldCorrectlyAdd() {
        iosTestApp.add(2, 4).verifyResult(6);
    }

}
