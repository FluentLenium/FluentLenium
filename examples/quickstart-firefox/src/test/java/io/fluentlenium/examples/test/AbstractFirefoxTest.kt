package io.fluentlenium.examples.test

import io.fluentlenium.adapter.junit.jupiter.FluentTest
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.BeforeAll

abstract class AbstractFirefoxTest : FluentTest() {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            WebDriverManager.firefoxdriver().setup()
        }
    }
}