package org.fluentlenium.kotest.matchers.page

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.annotation.Page
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.pages.FluentleniumPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPageWrongClassAnnotations
import org.fluentlenium.kotest.matchers.config.shouldAssert

class PageMatchersSpec : MatcherBase() {

    @Page
    lateinit var indexPage: IndexPage

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

        "titleNegative" {
            shouldAssert {
                indexPage shouldNot haveTitle("Fluent Selenium Documentation")
            }

            shouldAssert {
                indexPage.shouldNotHaveTitle("Fluent Selenium Documentation")
            }
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

        "haveElementNegative" {
            shouldAssert {
                indexPage shouldNot haveElement(el("h1"))
            }
            shouldAssert {
                indexPage.shouldNotHaveElement(el("h1"))
            }
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

        "haveElementDisplayedNegative" {
            shouldAssert {
                indexPage shouldNot haveElementDisplayed(el("h1"))
            }
            shouldAssert {
                indexPage.shouldNotHaveElementDisplayed(el("h1"))
            }
        }

        "haveElements" {
            indexPage should haveElements(jq("h1"))
            indexPage.shouldHaveElements(jq("h1"))

            indexPage shouldNot haveElements(jq("h2"))
            indexPage.shouldNotHaveElements(jq("h2"))
        }

        "haveElementsNegative" {
            shouldAssert {
                indexPage shouldNot haveElements(jq("h1"))
            }
            shouldAssert {
                indexPage.shouldNotHaveElements(jq("h1"))
            }
        }
    }
}
