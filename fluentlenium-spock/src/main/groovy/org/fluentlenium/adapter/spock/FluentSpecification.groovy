package org.fluentlenium.adapter.spock

import org.fluentlenium.utils.SeleniumVersionChecker
import org.fluentlenium.adapter.junit.FluentTestRule
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class FluentSpecification extends SpockAdapter {

    @Rule
    public TestRule watchman = new FluentTestRule(this) {
        @Override
        void starting(Description description) {
            SeleniumVersionChecker.checkSeleniumVersion()
            super.starting(description)
            specStarting(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void finished(Description description) {
            super.finished(description)
            specFinished(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void failed(Throwable e, Description description) {
            super.failed(e, description)
            specFailed(e, description.getTestClass(), description.getDisplayName())
        }
    }

    @ClassRule
    public static TestRule classWatchman = new TestRule() {

        @Override
        Statement apply(Statement base, Description description) {
            return new Statement() {

                @Override
                void evaluate() throws Throwable {
                    try {
                        base.evaluate()
                    } finally {
                        afterClass(description.getTestClass())
                    }
                }
            }
        }
    }

}
