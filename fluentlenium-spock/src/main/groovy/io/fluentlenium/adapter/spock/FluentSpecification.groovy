package io.fluentlenium.adapter.spock

import io.fluentlenium.adapter.junit.FluentTestRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description

import static io.fluentlenium.adapter.FluentTestRunnerAdapter.classDriverCleanup

class FluentSpecification extends SpockAdapter {

    @Rule
    public TestRule watchman = new FluentTestRule(this) {
        @Override
        void starting(Description description) {
            io.fluentlenium.utils.SeleniumVersionChecker.checkSeleniumVersion()
            super.starting(description)
            starting(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void finished(Description description) {
            super.finished(description)
            finished(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void failed(Throwable e, Description description) {
            super.failed(e, description)
            failed(e, description.getTestClass(), description.getDisplayName())
        }
    }

    def cleanupSpec() {
        classDriverCleanup(getClass())
    }

}
