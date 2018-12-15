package org.fluentlenium.adapter.cucumber.integration.inheritance;

import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.integration.inheritance.multiinheritance.MultiInheritanceRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.oldway.ClassicRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.driverperfeature.PerFeatureRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.getbean.GetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.java8.Java8Runner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.setbean.SetBeanRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.HookRunner;
import org.fluentlenium.adapter.cucumber.integration.inheritance.nodriver.NoWebDriverRunner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PerFeatureRunner.class,
        GetBeanRunner.class,
        SetBeanRunner.class,
        MultiInheritanceRunner.class,
        Java8Runner.class,
        NoWebDriverRunner.class,
        ClassicRunner.class,
        HookRunner.class
})
@NotThreadSafe
public class InheritanceSuite {
}
