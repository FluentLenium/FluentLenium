package io.fluentlenium.kotest.matchers.el

import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.kotest.assertions.shouldFail
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.openqa.selenium.By
import org.openqa.selenium.Dimension

class FluentWebElementMatchersSpec : MatcherBase({
    "present" {
        el("#doesNotExist") shouldNot bePresent()
        el("#doesNotExist").shouldNotBePresent()

        el("h1") should bePresent()
        el("h1").shouldBePresent()
    }

    "presentNegative" {
        shouldFail {
            el("#doesNotExist") should bePresent()
        }

        shouldFail {
            el("h1") shouldNot bePresent()
        }
    }

    "enabled" {
        el("#alertButton") should beEnabled()
        el("#alertButton").shouldBeEnabled()

        el("#disabledButton") shouldNot beEnabled()
        el("#disabledButton").shouldNotBeEnabled()
    }

    "enabledNegative" {
        shouldFail {
            el("#alertButton") shouldNot beEnabled()
        }

        shouldFail {
            el("#disabledButton") should beEnabled()
        }
    }

    "displayed" {
        el("h1") should beDisplayed()
        el("h1").shouldBeDisplayed()

        el("#hiddenElement") should bePresent()
        el("#hiddenElement") shouldNot beDisplayed()
        el("#hiddenElement").shouldNotBeDisplayed()
    }

    "displayedNegative" {
        shouldFail {
            el("h1") shouldNot beDisplayed()
        }

        shouldFail {
            el("#hiddenElement") should beDisplayed()
        }
    }

    "clickable" {
        el("#alertButton") should beClickable()
        el("#alertButton").shouldBeClickable()

        el("#disabledButton") shouldNot beClickable()
        el("#disabledButton").shouldNotBeClickable()
    }

    "clickableNegative" {
        shouldFail {
            el("#alertButton") shouldNot beClickable()
        }

        shouldFail {
            el("#disabledButton") should beClickable()
        }
    }

    "selected" {
        el("#choice #second") should beSelected()
        el("#choice #second").shouldBeSelected()

        el("#choice #first") shouldNot beSelected()
        el("#choice #first").shouldNotBeSelected()
    }

    "selectedNegative" {
        shouldFail {
            el("#choice #second") shouldNot beSelected()
        }

        shouldFail {
            el("#choice #first") should beSelected()
        }
    }

    "containText" {
        el("#oneline") should containText("A single line of text")
        el("#oneline") should containText("A single line")
        el("#oneline").shouldContainText("A single line")

        el("#oneline") shouldNot containText("other text")
        el("#oneline").shouldNotContainText("other text")
    }

    "containTextNegative" {
        shouldFail {
            el("#oneline") shouldNot containText("A single line")
        }

        shouldFail {
            el("#oneline") should containText("other text")
        }
    }

    "matchText" {
        el("#oneline") should matchText("A single.*text")
        el("#oneline").shouldMatchText("A single.*text")

        el("#oneline") shouldNot matchText("foo.*abc")
        el("#oneline").shouldNotMatchText("foo.*abc")
    }

    "matchTextNegative" {
        shouldFail {
            el("#oneline") shouldNot matchText("A single.*text")
        }

        shouldFail {
            el("#oneline") should matchText("foo.*abc")
        }
    }

    "haveId" {
        el("#oneline") should haveId("oneline")
        el("#oneline").shouldHaveId("oneline")

        el("#oneline") shouldNot haveId("other")
        el("#oneline").shouldNotHaveId("other")
    }

    "haveIdNegative" {
        shouldFail {
            el("#oneline") should haveId("other")
        }
        shouldFail {
            el("#oneline") shouldNot haveId("oneline")
        }
    }

    "haveClass" {
        el("span.child") should haveClass("child")
        el("span.child").shouldHaveClass("child")

        el("span.child") shouldNot haveClass("other")
        el("span.child").shouldNotHaveClass("other")

        el("#multiple-css-class") should haveClass("class1", "class2", "class3")
        el("#multiple-css-class") should haveClass("class1")
        el("#multiple-css-class") should haveClass("class1", "class2")
        el("#multiple-css-class") shouldNot haveClass("class1", "class2", "class4")
    }

    "haveClassNegative" {
        shouldFail {
            el("span.child") shouldNot haveClass("child")
        }

        shouldFail {
            el("span.child") should haveClass("other")
        }
    }

    "haveValue" {
        el("#choice #first") should haveValue("first")
        el("#choice #first").shouldHaveValue("first")

        el("#choice #first") shouldNot haveValue("second")
        el("#choice #first").shouldNotHaveValue("second")
    }

    "haveValueNegative" {
        shouldFail {
            el("#choice #first") shouldNot haveValue("first")
        }

        shouldFail {
            el("#choice #first") should haveValue("second")
        }
    }

    "haveName" {
        el(By.name("name")) should haveName("name")
        el(By.name("name")).shouldHaveName("name")

        el(By.name("name")) shouldNot haveName("other")
        el(By.name("name")).shouldNotHaveName("other")
    }

    "haveNameNegative" {
        shouldFail {
            el(By.name("name")) shouldNot haveName("name")
        }

        shouldFail {
            el(By.name("name")) should haveName("other")
        }
    }

    "haveTagName" {
        el("h1") should haveTagName("h1")
        el("h1").shouldHaveTagName("h1")

        el("h1") shouldNot haveTagName("h2")
        el("h1").shouldNotHaveTagName("h2")
    }

    "haveTagNameNegative" {
        shouldFail {
            el("h1") shouldNot haveTagName("h1")
        }

        shouldFail {
            el("h1") should haveTagName("h2")
        }
    }

    "haveDimension" {
        el("h1") should haveDimension(Dimension(1184, 37))
        el("h1") should haveDimension(1184 to 37)
        el("h1").shouldHaveDimension(Dimension(1184, 37))
        el("h1").shouldHaveDimension(1184 to 37)

        el("h1") shouldNot haveDimension(Dimension(100, 37))
        el("h1").shouldNotHaveDimension(Dimension(100, 37))
        el("h1").shouldNotHaveDimension(100 to 37)
    }

    "haveDimensionNegative" {
        shouldFail {
            el("h1") shouldNot haveDimension(1184 to 37)
        }

        shouldFail {
            el("h1") should haveDimension(Dimension(100, 37))
        }
    }

    "haveAttribute" {
        el("#choice #first") should haveAttribute("value")
        el("#choice #first").shouldHaveAttribute("value")

        el("#choice #first") shouldNot haveAttribute("other")
        el("#choice #first").shouldNotHaveAttribute("other")

        el("#choice #first") should haveAttributeValue("value", "first")
        el("#choice #first").shouldHaveAttributeValue("value", "first")
        el("#choice #first").shouldHaveAttributeValue("value" to "first")

        el("#choice #first") shouldNot haveAttributeValue("value", "other")
        el("#choice #first").shouldNotHaveAttributeValue("value", "other")
        el("#choice #first").shouldNotHaveAttributeValue("value" to "other")
    }

    "haveAttributeNegative" {
        shouldFail {
            el("#choice #first") shouldNot haveAttribute("value")
        }

        shouldFail {
            el("#choice #first") should haveAttribute("other")
        }

        shouldFail {
            el("#choice #first") shouldNot haveAttributeValue("value", "first")
        }

        shouldFail {
            el("#choice #first") should haveAttributeValue("value", "other")
        }
    }
})
