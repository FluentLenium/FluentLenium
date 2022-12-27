package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.configuration.ConfigurationProperties
import io.fluentlenium.configuration.CustomProperty
import io.fluentlenium.configuration.FluentConfiguration

@_root_ide_package_.io.fluentlenium.configuration.FluentConfiguration(
    driverLifecycle = _root_ide_package_.io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM,
    custom = [_root_ide_package_.io.fluentlenium.configuration.CustomProperty(name = "foo", value = "bar")]
)
class ConfigurationSpec : FluentDescribeSpec() {

    override fun getBrowserTimeout(): Long = 4711

    init {
        setCustomProperty("customProp", "myValue")

        it("driverLifeCycle via annotation") {
            driverLifecycle shouldBe _root_ide_package_.io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM
        }

        it("custom property via annotation") {
            getCustomProperty("foo") shouldBe "bar"
        }

        it("browser timeout via property getter override") {
            browserTimeout shouldBe 4711
        }

        it("custom property via property setter from outside") {
            getCustomProperty("customProp") shouldBe "myValue"
        }

        it("custom property via property setter") {
            setCustomProperty("customProp", "myCustomValue")

            getCustomProperty("customProp") shouldBe "myCustomValue"
        }

        it("property from .properties") {
            awaitAtMost shouldBe 30001
        }
    }
}
