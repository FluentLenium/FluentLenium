package org.fluentlenium.adapter.cucumber.integration.noinheritance;

import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.integration.noinheritance.basic.BasicRunner;
import org.fluentlenium.adapter.cucumber.integration.noinheritance.factory.FactoryRunner;
import org.fluentlenium.adapter.cucumber.integration.noinheritance.java8.Java8Runner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BasicRunner.class,
        FactoryRunner.class,
        Java8Runner.class
})
@NotThreadSafe
public class NoInheritanceSuite {
}
