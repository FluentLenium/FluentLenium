package org.fluentlenium.adapter.cucumber.integration.tests;

import org.fluentlenium.adapter.cucumber.integration.tests.driverperfeature.PerFeatureRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.getbean.GetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.java8.Java8Runner;
import org.fluentlenium.adapter.cucumber.integration.tests.multiinheritance.MultiInheritanceRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.nodriver.NoWebDriverRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.setbean.SetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.tests.waithook.HookRunner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        PerFeatureRunner.class,
        GetBeanRunner.class,
        SetBeanRunner.class,
        MultiInheritanceRunner.class,
        Java8Runner.class,
        NoWebDriverRunner.class,
        HookRunner.class
})
public class IntegrationTestSuite {
}
