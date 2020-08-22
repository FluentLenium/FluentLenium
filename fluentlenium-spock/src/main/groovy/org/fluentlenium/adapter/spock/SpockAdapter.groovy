package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.FluentTestRunnerAdapter
class SpockAdapter extends FluentTestRunnerAdapter {

    private SpockAdapter() {
        super()
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
