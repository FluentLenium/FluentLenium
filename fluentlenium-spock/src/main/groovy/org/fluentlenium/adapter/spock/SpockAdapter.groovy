package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.configuration.Configuration

final class SpockAdapter extends FluentTestRunnerAdapter {

    protected Configuration configuration

    private SpockAdapter() {
        super()
        this.configuration = configuration
    }

    void specStarting(Class<?> testClass, String testName) {
        super.starting(testClass, testName)
    }

    void specFinished(Class<?> testClass, String testName) {
        super.finished(testClass, testName)
    }

    void specFailed(Throwable e, Class<?> testClass, String testName) {
        super.failed(e, testClass, testName)
    }

}
