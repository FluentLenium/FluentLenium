package io.fluentlenium.examples.test

import io.fluentlenium.adapter.junit.jupiter.FluentTest
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.os.ExecutableFinder

abstract class AbstractFirefoxTest : FluentTest() {
    companion object {
        private const val PATH_TO_GECKO_DRIVER = "C:\\drivers\\geckodriver.exe"
        private const val GECKO_DRIVER_PROPERTY = "webdriver.gecko.driver"

        @JvmStatic
        @BeforeAll
        fun setup() {
            if (systemPropertyNotSet() && executableNotPresentInPath()) {
                setSystemProperty()
            }
        }

        private fun executableNotPresentInPath(): Boolean {
            return ExecutableFinder().find("geckodriver") == null
        }

        private fun systemPropertyNotSet(): Boolean {
            return System.getProperty(GECKO_DRIVER_PROPERTY) == null
        }

        private fun setSystemProperty() {
            System.setProperty(GECKO_DRIVER_PROPERTY, PATH_TO_GECKO_DRIVER)
        }
    }
}