package io.fluentlenium.adapter.cucumber.integration.tests;

import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.PerFeatureRunner;
import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.getbean.GetBeanRunner;
import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.multiinheritance.MultiInheritanceRunner;
import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.nodriver.NoWebDriverRunner;
import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.setbean.SetBeanRunner;
import io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.waithook.HookRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        PerFeatureRunner.class,
        GetBeanRunner.class,
        SetBeanRunner.class,
        MultiInheritanceRunner.class,
        NoWebDriverRunner.class,
        HookRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.driverperfeature.PerFeatureRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.getbean.GetBeanRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.setbean.SetBeanRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.multiinheritance.MultiInheritanceRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.nodriver.NoWebDriverRunner.class,
        io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.waithook.HookRunner.class
})
public class IntegrationTestSuite {

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
