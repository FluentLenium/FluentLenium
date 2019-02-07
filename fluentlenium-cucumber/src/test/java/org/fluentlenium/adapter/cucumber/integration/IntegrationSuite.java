package org.fluentlenium.adapter.cucumber.integration;

import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.integration.inheritance.InheritanceSuite;
import org.fluentlenium.adapter.cucumber.integration.noinheritance.NoInheritanceSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
        NoInheritanceSuite.class,
        InheritanceSuite.class})
@NotThreadSafe
public class IntegrationSuite {
}
