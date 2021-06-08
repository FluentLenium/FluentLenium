package org.fluentlenium.kotest.matchers.page

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.annotation.Page
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.pages.FluentleniumPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPageNoClassAnnotations
import org.fluentlenium.kotest.matchers.config.pages.IndexPageWrongClassAnnotations

class PageMatchersSpec : MatcherBase() {

    @Page
    lateinit var indexPage: IndexPage

    @Page
    lateinit var indexPageNoClassAnnotations: IndexPageNoClassAnnotations

    @Page
    lateinit var indexPageWrongClassAnnotation: IndexPageWrongClassAnnotations

    @Page
    lateinit var fluentleniumPage: FluentleniumPage

    init {
        "pageWithoutUrl" {
            indexPage shouldNot haveExpectedUrl()
            indexPage.shouldNotHaveExpectedUrl()
        }

        "pageWithUrl" {
            fluentleniumPage shouldNot haveExpectedUrl()

            goTo(fluentleniumPage)
            fluentleniumPage should haveExpectedUrl()
            fluentleniumPage.shouldHaveExpectedUrl()
        }

        "haveExpectedelements" {
            indexPageWrongClassAnnotation shouldNot haveExpectedElements()
            indexPageWrongClassAnnotation.shouldNotHaveExpectedElements()

            indexPage.shouldHaveExpectedElements()
            indexPage.shouldHaveExpectedElements()
        }

        "title" {
            indexPage should haveTitle("Fluent Selenium Documentation")
            indexPage.shouldHaveTitle("Fluent Selenium Documentation")

            indexPage.shouldNotHaveTitle("Wrong title")
            indexPage shouldNot haveTitle("Wrong title")
        }

        "page without title" {
            goToFile("noTitle.html")

            indexPage shouldNot haveTitle("Wrong title")
        }

        "haveElement" {
            indexPage should haveElement(el("h1"))
            indexPage.shouldHaveElement(el("h1"))

            indexPage shouldNot haveElement(el("h2"))
            indexPage.shouldNotHaveElement(el("h2"))
        }

        "haveElementDisplayed" {
            indexPage should haveElementDisplayed(el("h1"))
            indexPage.shouldHaveElementDisplayed(el("h1"))

            indexPage shouldNot haveElementDisplayed(el("#doesNotExist"))
            indexPage.shouldNotHaveElementDisplayed(el("#doesNotExist"))

            indexPage.shouldHaveElement(el("#hiddenElement"))
            indexPage shouldNot haveElementDisplayed(el("#hiddenElement"))
            indexPage.shouldNotHaveElementDisplayed(el("#hiddenElement"))
        }

        "haveElements" {
            indexPage should haveElements(jq("h1"))
            indexPage.shouldHaveElements(jq("h1"))

            indexPage shouldNot haveElements(jq("h2"))
            indexPage.shouldNotHaveElements(jq("h2"))
        }
    }

}