package org.fluentlenium.kotest.matchers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.fluentlenium.core.alert.Alert
import org.fluentlenium.kotest.haveText
import org.fluentlenium.kotest.shouldBePresent
import org.fluentlenium.kotest.shouldHaveText
import org.fluentlenium.kotest.shouldNotBePresent
import org.openqa.selenium.NoAlertPresentException

class AlertMatchersSpec : AnnotationSpec() {

    val alert: Alert = mockk()

    @Test
    fun testHasTextPositive() {
        every { alert.text } returns "some text"

        alert should haveText("some text")
        alert shouldNot haveText("some other text")
    }

    @Test
    fun testHasTextNegative() {
        every { alert.text } returns "other text"

        shouldThrow<AssertionError> {
            alert should haveText("some text")
        }
        shouldThrow<AssertionError> {
            alert shouldNot haveText("other text")
        }
    }

    @Test
    @Ignore
    fun testHasTextNotPresent() {
        every { alert.text } throws NoAlertPresentException()

        shouldThrow<AssertionError> {
            alert.shouldHaveText("some text")
        }
    }

    @Test
    fun testIsPresentPositive() {
        every { alert.present() } returns true

        alert.shouldBePresent()
        verify { alert.present() }
    }

    @Test
    fun testIsPresentNegative() {
        every { alert.present() } returns false

        alert.shouldNotBePresent()

        shouldThrow<AssertionError> {
            alert.shouldBePresent()
        }
    }
}