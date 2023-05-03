package io.fluentlenium.adapter.spock


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
