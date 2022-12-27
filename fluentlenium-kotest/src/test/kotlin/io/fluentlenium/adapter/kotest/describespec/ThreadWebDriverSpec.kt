package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.jq
import io.fluentlenium.configuration.ConfigurationProperties
import io.fluentlenium.configuration.FluentConfiguration

@_root_ide_package_.io.fluentlenium.configuration.FluentConfiguration(
    driverLifecycle = _root_ide_package_.io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.THREAD
)
class ThreadWebDriverSpec : FluentDescribeSpec({

    it("driverLifeCycle via annotation") {
        driverLifecycle shouldBe _root_ide_package_.io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.THREAD
    }

    it("Title should be correct") {
        goTo(DEFAULT_URL)
        jq("#name").fill().with("FluentLenium")
        el("#name").value() shouldBe "FluentLenium"
        window().title() shouldContain "Fluent"
    }
})
