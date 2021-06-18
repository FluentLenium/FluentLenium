package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.FluentControlContainer
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer
import org.fluentlenium.configuration.Configuration
import org.fluentlenium.configuration.ConfigurationFactoryProvider
import org.fluentlenium.core.FluentControl
import spock.lang.Specification

class SpockControl extends Specification implements FluentControl {

    private FluentControlContainer controlContainer = new ThreadLocalFluentControlContainer()
    private Configuration configuration = ConfigurationFactoryProvider.newConfiguration(getClass())

    @Override
    FluentControlContainer getControlContainer() {
        return controlContainer
    }

    @Override
    FluentControl getFluentControl() {
        return controlContainer.getFluentControl()
    }

    @Override
    Configuration getConfiguration() {
        return configuration
    }

}
