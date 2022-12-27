package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.nodriver;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class NoWebDriverRunner {
    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }
}
