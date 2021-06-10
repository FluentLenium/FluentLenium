package org.fluentlenium.kotest.matchers.fluentlist

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.openqa.selenium.Dimension

class FluentListMatchersSpec : MatcherBase(
    {
        "can use collection asserts" {
            jq("#doesNotExist").shouldBeEmpty()
            jq("h1").shouldHaveSize(1)
        }

        "haveText" {
            jq("#choice option") should haveText("First Value")
            jq("#choice option").shouldHaveText("First Value")

            jq("#choice option") shouldNot haveText("Not")
            jq("#choice option").shouldNotHaveText("Not")
        }

        "haveTextContaining" {
            jq("#choice option") should haveTextContaining("First V")
            jq("#choice option").shouldHaveTextContaining("First V")

            jq("#choice option") shouldNot haveTextContaining("Not")
            jq("#choice option").shouldNotHaveTextContaining("Not")
        }

        "haveTextMatching" {
            jq("#choice option") should haveTextMatching("First V.*")
            jq("#choice option").shouldHaveTextMatching("First V.*")

            jq("#choice option") shouldNot haveTextMatching("Not")
            jq("#choice option").shouldNotHaveTextMatching("Not")
        }

        "haveId" {
            jq("#choice option") should haveId("first")
            jq("#choice option").shouldHaveId("first")

            jq("#choice option") shouldNot haveId("other")
            jq("#choice option").shouldNotHaveId("other")
        }

        "haveClass" {
            jq("button") should haveClass("class1")
            jq("button") should haveClass("class1", "class2")
            jq("button") should haveClass("class1", "class2", "class3")
            jq("button") shouldNot haveClass("class1", "class2", "class10")

            jq("button").shouldHaveClass("class1")

            jq("button") shouldNot haveClass("classx")
            jq("button").shouldNotHaveClass("classx")
        }

        "haveName" {
            jq("span.small") should haveName("name")
            jq("span.small").shouldHaveName("name")
            jq("span.small").shouldHaveName("name")

            jq("span.small") shouldNot haveName("other")
            jq("span.small").shouldNotHaveName("other")
        }

        "haveValue" {
            jq("#choice option") should haveValue("first")
            jq("#choice option").shouldHaveValue("first")

            jq("#choice option") shouldNot haveValue("other")
            jq("#choice option").shouldNotHaveValue("other")
        }

        "haveAttribute" {
            jq("#choice option") should haveAttribute("value")
            jq("#choice option").shouldHaveAttribute("value")

            jq("#choice option") shouldNot haveAttribute("other")
            jq("#choice option").shouldNotHaveAttribute("other")

            jq("#choice option") should haveAttributeValue(
                "value",
                "first"
            )
            jq("#choice option").shouldHaveAttributeValue("value", "first")
            jq("#choice option").shouldHaveAttributeValue("value" to "first")

            jq("#choice option") shouldNot haveAttributeValue(
                "value",
                "other"
            )
            jq("#choice #first").shouldNotHaveAttributeValue("value", "other")
            jq("#choice #first").shouldNotHaveAttributeValue("value" to "other")
        }

        "haveTagName" {
            jq("h1") should haveTagName("h1")
            jq("h1").shouldHaveTagName("h1")

            jq("h1") shouldNot haveTagName("h2")
            jq("h1").shouldNotHaveTagName("h2")
        }

        "haveDimension" {
            jq("h1") should haveDimension(Dimension(784, 37))
            jq("h1") should haveDimension(784 to 37)
            jq("h1").shouldHaveDimension(Dimension(784, 37))

            jq("h1") shouldNot haveDimension(Dimension(100, 37))
            jq("h1").shouldNotHaveDimension(Dimension(100, 37))
        }
    }
)
