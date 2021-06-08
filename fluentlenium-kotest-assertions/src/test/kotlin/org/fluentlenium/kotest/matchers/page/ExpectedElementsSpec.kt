package org.fluentlenium.kotest.matchers.page

import io.kotest.core.test.TestCase
import io.kotest.matchers.should
import org.fluentlenium.adapter.kotest.FluentAnnotationSpec
import org.fluentlenium.core.annotation.Page
import org.fluentlenium.kotest.matchers.haveExpectedElements
import org.fluentlenium.kotest.matchers.page.pages.IndexPage
import org.fluentlenium.kotest.matchers.page.pages.IndexPageNoClassAnnotations
import org.fluentlenium.kotest.matchers.page.pages.IndexPageWrongClassAnnotations
import org.fluentlenium.utils.UrlUtils

class ExpectedElementsSpec : FluentAnnotationSpec() {

    protected val DEFAULT_URL = UrlUtils.getAbsoluteUrlFromFile("index.html")

    @Page
    lateinit var indexPage: IndexPage

    @Page
    lateinit var indexPageNoClassAnnotations: IndexPageNoClassAnnotations

    @Page
    lateinit var indexPageWrongClassAnnotation: IndexPageWrongClassAnnotations

    override fun beforeTest(testCase: TestCase) {
        goTo(DEFAULT_URL)
    }

    @Test
    fun verifyHasExpectedElements() {
        indexPage should haveExpectedElements()
    }

}