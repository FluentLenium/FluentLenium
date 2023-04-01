package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.configuration.ConfigurationProperties
import io.fluentlenium.configuration.FluentConfiguration
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

@FluentConfiguration(
    driverLifecycle = ConfigurationProperties.DriverLifecycle.THREAD,
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
