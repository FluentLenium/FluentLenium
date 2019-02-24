package org.fluentlenium.adapter.spock

import org.fluentlenium.utils.SeleniumVersionChecker
import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.junit.FluentTestRule
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.search.SearchFilter
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import spock.lang.Specification

class FluentSpecification extends Specification {

    @Delegate
    private FluentSpecificationAdapter fl = new FluentSpecificationAdapter()

    FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return fl.$(rawElements)
    }

    FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return fl.$(locator, filters)
    }

    FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return fl.$(selector, filters)
    }

    FluentList<FluentWebElement> $(SearchFilter... filters) {
        return fl.$(filters)
    }

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
                        FluentTestRunnerAdapter.afterClass(description.getTestClass())
                    }
                }
            }
        }
    }

}
