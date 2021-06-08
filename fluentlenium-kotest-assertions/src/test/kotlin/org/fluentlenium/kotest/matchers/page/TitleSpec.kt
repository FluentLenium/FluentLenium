package org.fluentlenium.kotest.matchers.page

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.test.TestCase
import io.kotest.matchers.should
import org.fluentlenium.adapter.kotest.FluentAnnotationSpec
import org.fluentlenium.core.annotation.Page
import org.fluentlenium.kotest.matchers.haveExpectedUrl
import org.fluentlenium.kotest.matchers.haveTitle
import org.fluentlenium.kotest.matchers.haveUrl
import org.fluentlenium.kotest.matchers.page.pages.IndexPage
import org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile

class TitleSpec : FluentAnnotationSpec() {

    protected val DEFAULT_URL = getAbsoluteUrlFromFile("index.html")

    @Page
    lateinit var indexPage: IndexPage


    override fun beforeTest(testCase: TestCase) {
        goTo(DEFAULT_URL)
    }

    @Test
    fun verifyHasTitle() {
        indexPage should haveTitle("Fluent Selenium Documentation")
    }

    @Test
    fun verifyHasTitleNegative() {
        shouldThrow<AssertionError> {
            indexPage should haveTitle("Wrong title")
        }
    }

    @Test
    fun verifyHasUrlNegative() {
        shouldThrow<AssertionError> {
            indexPage should haveUrl("https://fluentlenium.com")
        }
    }

    @Test
    fun verifyHasExpectedElementseNegativeAbsent() {
        shouldThrow<AssertionError> {
            indexPage should haveExpectedUrl()
        }
    }
}


