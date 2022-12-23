package org.fluentlenium.adapter.cucumber.integration.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.PerFeatureRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.getbean.GetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.multiinheritance.MultiInheritanceRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.nodriver.NoWebDriverRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.setbean.SetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.waithook.HookRunner;
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
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.driverperfeature.PerFeatureRunner.class,
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.getbean.GetBeanRunner.class,
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.setbean.SetBeanRunner.class,
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.multiinheritance.MultiInheritanceRunner.class,
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.nodriver.NoWebDriverRunner.class,
        org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.waithook.HookRunner.class
})
public class IntegrationTestSuite {

    @BeforeClass
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

}
