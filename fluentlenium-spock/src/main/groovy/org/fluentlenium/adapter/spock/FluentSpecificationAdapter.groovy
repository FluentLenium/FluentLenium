package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.DefaultSharedMutator
import org.fluentlenium.adapter.FluentTestRunnerAdapter

class FluentSpecificationAdapter extends FluentTestRunnerAdapter {

    FluentSpecificationAdapter() {
        super(new DefaultSharedMutator())
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
