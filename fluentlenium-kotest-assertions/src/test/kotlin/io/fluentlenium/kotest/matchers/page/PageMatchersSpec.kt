package io.fluentlenium.kotest.matchers.page

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.annotation.Page
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.pages.FluentleniumPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPage
import org.fluentlenium.kotest.matchers.config.pages.IndexPageWrongClassAnnotations
import org.fluentlenium.kotest.matchers.config.shouldAssert

class PageMatchersSpec : MatcherBase() {

    @_root_ide_package_.io.fluentlenium.core.annotation.Page
    lateinit var indexPage: IndexPage

    @_root_ide_package_.io.fluentlenium.core.annotation.Page
    lateinit var indexPageWrongClassAnnotation: IndexPageWrongClassAnnotations

    @_root_ide_package_.io.fluentlenium.core.annotation.Page
    lateinit var fluentleniumPage: FluentleniumPage

    init {
        "haveUrl" {
            goTo(fluentleniumPage)

            indexPage should haveUrl("https://fluentlenium.io/")
            indexPage shouldNot haveUrl("https://acme.com/")
        }

        "haveUrlNegative" {
            goTo(fluentleniumPage)

            shouldAssert {
                indexPage shouldNot haveUrl("https://fluentlenium.io/")
            }

            shouldAssert {
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
            shouldAssert {
                fluentleniumPage should haveExpectedUrl()
            }

            goTo(fluentleniumPage)

            shouldAssert {
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
            shouldAssert {
                indexPageWrongClassAnnotation should haveExpectedElements()
            }

            shouldAssert {
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
            shouldAssert {
                indexPage shouldNot haveTitle("Fluent Selenium Documentation")
            }

            shouldAssert {
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
            shouldAssert {
                indexPage shouldNot haveElement(el("h1"))
            }
            shouldAssert {
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
            shouldAssert {
                indexPage shouldNot haveElementDisplayed(el("h1"))
            }
            shouldAssert {
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
            shouldAssert {
                indexPage shouldNot haveElements(jq("h1"))
            }
            shouldAssert {
                indexPage should haveElements(jq("h2"))
            }
        }
    }
}
