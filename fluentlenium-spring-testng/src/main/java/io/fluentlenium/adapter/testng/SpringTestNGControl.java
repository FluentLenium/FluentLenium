package io.fluentlenium.adapter.testng;

import io.fluentlenium.adapter.FluentControlContainer;import io.fluentlenium.configuration.Configuration;import io.fluentlenium.configuration.ConfigurationFactoryProvider;import io.fluentlenium.core.FluentControl;import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.configuration.ConfigurationFactoryProvider;
import io.fluentlenium.core.FluentControl;
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
