package org.fluentlenium.kotest.matchers.el

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.shouldAssert
import org.openqa.selenium.By
import org.openqa.selenium.Dimension

class FluentWebElementMatchersSpec : MatcherBase({
    "present" {
        el("#doesNotExist") shouldNot bePresent()
        el("#doesNotExist").shouldNotBePresent()

        el("h1") should bePresent()
        el("h1").shouldBePresent()
    }

    "enabled" {
        el("#alertButton") should beEnabled()
        el("#alertButton").shouldBeEnabled()

        el("#disabledButton") shouldNot beEnabled()
        el("#disabledButton").shouldNotBeEnabled()
    }

    "displayed" {
        el("h1") should beDisplayed()
        el("h1").shouldBeDisplayed()

        el("#hiddenElement") should bePresent()
        el("#hiddenElement") shouldNot beDisplayed()
        el("#hiddenElement").shouldNotBeDisplayed()
    }

    "clickable" {
        el("#alertButton") should beClickable()
        el("#alertButton").shouldBeClickable()

        el("#disabledButton") shouldNot beClickable()
        el("#disabledButton").shouldNotBeClickable()
    }

    "selected" {
        el("#choice #second") should beSelected()
        el("#choice #second").shouldBeSelected()

        el("#choice #first") shouldNot beSelected()
        el("#choice #first").shouldNotBeSelected()
    }

    "containText" {
        el("#oneline") should containText("A single line of text")
        el("#oneline") should containText("A single line")
        el("#oneline").shouldContainText("A single line")

        el("#oneline") shouldNot containText("other text")
        el("#oneline").shouldNotContainText("other text")
    }

    "matchText" {
        el("#oneline") should matchText("A single.*text")
        el("#oneline").shouldMatchText("A single.*text")

        el("#oneline") shouldNot matchText("foo.*abc")
        el("#oneline").shouldNotMatchText("foo.*abc")
    }

    "haveId" {
        el("#oneline") should haveId("oneline")
        el("#oneline").shouldHaveId("oneline")

        el("#oneline") shouldNot haveId("other")
        el("#oneline").shouldNotHaveId("other")
    }

    "haveIdNegative" {
        shouldAssert {
            el("#oneline") should haveId("other")
        }
        shouldAssert {
            el("#oneline").shouldHaveId("other")
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

    "haveValue" {
        el("#choice #first") should haveValue("first")
        el("#choice #first").shouldHaveValue("first")

        el("#choice #first") shouldNot haveValue("second")
        el("#choice #first").shouldNotHaveValue("second")
    }

    "haveName" {
        el(By.name("name")) should haveName("name")
        el(By.name("name")).shouldHaveName("name")

        el(By.name("name")) shouldNot haveName("other")
        el(By.name("name")).shouldNotHaveName("other")
    }

    "haveTagName" {
        el("h1") should haveTagName("h1")
        el("h1").shouldHaveTagName("h1")

        el("h1") shouldNot haveTagName("h2")
        el("h1").shouldNotHaveTagName("h2")
    }

    "haveDimension" {
        el("h1") should haveDimension(Dimension(784, 37))
        el("h1") should haveDimension(784 to 37)
        el("h1").shouldHaveDimension(Dimension(784, 37))

        el("h1") shouldNot haveDimension(Dimension(100, 37))
        el("h1").shouldNotHaveDimension(Dimension(100, 37))
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
})
