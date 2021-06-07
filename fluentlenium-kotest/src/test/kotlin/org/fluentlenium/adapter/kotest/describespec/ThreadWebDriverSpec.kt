package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.configuration.FluentConfiguration

@FluentConfiguration(
    driverLifecycle = ConfigurationProperties.DriverLifecycle.THREAD
)
class ThreadWebDriverSpec : FluentDescribeSpec({

    it("driverLifeCycle via annotation") {
        driverLifecycle shouldBe ConfigurationProperties.DriverLifecycle.THREAD
    }

    it("Title should be correct") {
        goTo(DEFAULT_URL)
        jq("#name").fill().with("FluentLenium")
        el("#name").value() shouldBe "FluentLenium"
        window().title() shouldContain "Fluent"
    }
})
