package org.fluentlenium.adapter.spock

import org.fluentlenium.utils.SeleniumVersionChecker
import org.fluentlenium.adapter.junit.FluentTestRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description

import static org.fluentlenium.adapter.FluentTestRunnerAdapter.afterClass

class FluentSpecification extends SpockAdapter {

    @Rule
    public TestRule watchman = new FluentTestRule(this) {
        @Override
        void starting(Description description) {
            SeleniumVersionChecker.checkSeleniumVersion()
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
        afterClass(getClass())
    }

}
