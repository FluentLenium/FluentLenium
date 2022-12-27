package io.fluentlenium.adapter.kotest.describespec

import io.kotest.core.TestConfiguration
import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.openqa.selenium.WebDriver

class CanUseAdditionalExtensionSpec : FluentDescribeSpec() {

    var beforeTestListenerRun = false

    override fun newWebDriver(): WebDriver {
        // This is invoked by the Fluentlenium Integration
        // with this assertion we ensure that our custom extension (below) already was run
        beforeTestListenerRun shouldBe true

        return super.newWebDriver()
    }

    fun registerFirst(vararg extensions: Extension) {
        val field = TestConfiguration::class.java.getDeclaredField("_extensions")
        field.isAccessible = true

        val previousValue: List<Extension> = field.get(this) as List<Extension>

        val newValue = extensions.toList() + previousValue

        field.set(this, newValue)
    }

    init {
        // when using register here the extensions will be run in a wrong order
        registerFirst(object : BeforeTestListener {
            override suspend fun beforeTest(testCase: TestCase) {
                beforeTestListenerRun = true
            }
        })

        it("additional extension") {
        }
    }
}
