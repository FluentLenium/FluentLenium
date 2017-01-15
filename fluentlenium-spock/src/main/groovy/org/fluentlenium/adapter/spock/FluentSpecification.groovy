package org.fluentlenium.adapter.spock

import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.junit.FluentTestRule
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import spock.lang.Specification

class FluentSpecification extends Specification {

    protected FluentSpecificationAdapter fl = new FluentSpecificationAdapter()

    @Rule
    public TestRule watchman = new FluentTestRule(this) {
        @Override
        void starting(Description description) {
            super.starting(description)
            fl.specStarting(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void finished(Description description) {
            super.finished(description)
            fl.specFinished(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void failed(Throwable e, Description description) {
            super.failed(e, description)
            fl.specFailed(e, description.getTestClass(), description.getDisplayName())
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
                        FluentTestRunnerAdapter.afterClass(description.getTestClass())
                    }
                }
            }
        }
    }

}
