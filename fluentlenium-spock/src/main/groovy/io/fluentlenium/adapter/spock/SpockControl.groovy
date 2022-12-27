package io.fluentlenium.adapter.spock


import io.fluentlenium.adapter.FluentControlContainer
import io.fluentlenium.adapter.ThreadLocalFluentControlContainer
import io.fluentlenium.configuration.Configuration
import io.fluentlenium.configuration.ConfigurationFactoryProvider
import io.fluentlenium.core.FluentControl
import spock.lang.Specification

class SpockControl extends Specification implements io.fluentlenium.core.FluentControl {

    private io.fluentlenium.adapter.FluentControlContainer controlContainer = new io.fluentlenium.adapter.ThreadLocalFluentControlContainer()
    private io.fluentlenium.configuration.Configuration configuration = io.fluentlenium.configuration.ConfigurationFactoryProvider.newConfiguration(getClass())

    @Override
    io.fluentlenium.adapter.FluentControlContainer getControlContainer() {
        return controlContainer
    }

    @Override
    io.fluentlenium.core.FluentControl getFluentControl() {
        return controlContainer.getFluentControl()
    }

    @Override
    io.fluentlenium.configuration.Configuration getConfiguration() {
        return configuration
    }

}
