package io.fluentlenium.kotest.matchers.page

import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.annotation.Page
import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.fluentlenium.kotest.matchers.config.pages.FluentleniumPage
import io.fluentlenium.kotest.matchers.config.pages.IndexPage
import io.fluentlenium.kotest.matchers.config.pages.IndexPageWrongClassAnnotations
import io.kotest.assertions.shouldFail
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot

class PageMatchersSpec : MatcherBase() {

    @Page
    lateinit var indexPage: IndexPage

    @Page
    lateinit var indexPageWrongClassAnnotation: IndexPageWrongClassAnnotations

    @Page
    lateinit var fluentleniumPage: FluentleniumPage

    init {
        "haveUrl" {
            goTo(fluentleniumPage)

            indexPage should haveUrl("https://fluentlenium.io/")
            indexPage shouldNot haveUrl("https://acme.com/")
        }

        "haveUrlNegative" {
            goTo(fluentleniumPage)

            shouldFail {
                indexPage shouldNot haveUrl("https://fluentlenium.io/")
            }

            shouldFail {
                indexPage should haveUrl("https://acme.com/")
            }
        }

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

        "haveExpectedUrlNegative" {
            shouldFail {
                fluentleniumPage should haveExpectedUrl()
            }

            goTo(fluentleniumPage)

            shouldFail {
                fluentleniumPage shouldNot haveExpectedUrl()
            }
        }

        "haveExpectedElements" {
            indexPageWrongClassAnnotation shouldNot haveExpectedElements()
            indexPageWrongClassAnnotation.shouldNotHaveExpectedElements()

            indexPage.shouldHaveExpectedElements()
            indexPage.shouldHaveExpectedElements()
        }

        "haveExpectedElementsNegative" {
            shouldFail {
                indexPageWrongClassAnnotation should haveExpectedElements()
            }

            shouldFail {
                indexPage shouldNot haveExpectedElements()
            }
        }

        "title" {
            indexPage should haveTitle("Fluent Selenium Documentation")
            indexPage.shouldHaveTitle("Fluent Selenium Documentation")

            indexPage.shouldNotHaveTitle("Wrong title")
            indexPage shouldNot haveTitle("Wrong title")
        }

        "titleNegative" {
            shouldFail {
                indexPage shouldNot haveTitle("Fluent Selenium Documentation")
            }

            shouldFail {
                indexPage should haveTitle("Wrong title")
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
            shouldFail {
                indexPage shouldNot haveElement(el("h1"))
            }
            shouldFail {
                indexPage should haveElement(el("h2"))
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
            shouldFail {
                indexPage shouldNot haveElementDisplayed(el("h1"))
            }
            shouldFail {
                indexPage should haveElementDisplayed(el("#doesNotExist"))
            }
        }

        "haveElements" {
            indexPage should haveElements(jq("h1"))
            indexPage.shouldHaveElements(jq("h1"))

            indexPage shouldNot haveElements(jq("h2"))
            indexPage.shouldNotHaveElements(jq("h2"))
        }

        "haveElementsNegative" {
            shouldFail {
                indexPage shouldNot haveElements(jq("h1"))
            }
            shouldFail {
                indexPage should haveElements(jq("h2"))
            }
        }
    }
}
