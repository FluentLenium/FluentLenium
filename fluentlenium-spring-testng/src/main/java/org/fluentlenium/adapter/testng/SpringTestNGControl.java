package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;
import org.fluentlenium.core.FluentControl;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

class SpringTestNGControl extends AbstractTestNGSpringContextTests implements FluentControl {

    private final FluentControlContainer controlContainer;
    private final Configuration configuration;

    SpringTestNGControl(FluentControlContainer controlContainer) {
        this.controlContainer = controlContainer;
        this.configuration = ConfigurationFactoryProvider.newConfiguration(getClass());
    }

    SpringTestNGControl(FluentControlContainer controlContainer, Configuration configuration) {
        this.controlContainer = controlContainer;
        this.configuration = configuration;
    }

    @Override
    public FluentControlContainer getControlContainer() {
        return controlContainer;
    }

    @Override
    public FluentControl getFluentControl() {
        return controlContainer.getFluentControl();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
