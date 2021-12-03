package org.fluentlenium.kotest.matchers.jq

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.shouldAssert
import org.openqa.selenium.Dimension

class FluentListMatchersSpec : MatcherBase(
    {
        "can use collection asserts" {
            jq("#doesNotExist").shouldBeEmpty()
            jq("h1").shouldHaveSize(1)
        }

        "present" {
            jq("#doesNotExist") shouldNot bePresent()
            jq("#doesNotExist").shouldNotBePresent()

            jq("h1") should bePresent()
            jq("h1").shouldBePresent()
        }

        "presentNegative" {
            shouldAssert {
                jq("#doesNotExist") should bePresent()
            }

            shouldAssert {
                jq("h1") shouldNot bePresent()
            }
        }

        "haveText" {
            jq("#choice option") should haveText("First Value")
            jq("#choice option").shouldHaveText("First Value")

            jq("#choice option") shouldNot haveText("Not")
            jq("#choice option").shouldNotHaveText("Not")
        }

        "haveTextNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveText("First Value")
            }

            shouldAssert {
                jq("#choice option") should haveText("Not")
            }
        }

        "haveTextContaining" {
            jq("#choice option") should haveTextContaining("First V")
            jq("#choice option").shouldHaveTextContaining("First V")

            jq("#choice option") shouldNot haveTextContaining("Not")
            jq("#choice option").shouldNotHaveTextContaining("Not")
        }

        "haveTextContainingNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveTextContaining("First V")
            }

            shouldAssert {
                jq("#choice option") should haveTextContaining("Not")
            }
        }

        "haveTextMatching" {
            jq("#choice option") should haveTextMatching("First V.*")
            jq("#choice option").shouldHaveTextMatching("First V.*")

            jq("#choice option") shouldNot haveTextMatching("Not")
            jq("#choice option").shouldNotHaveTextMatching("Not")
        }

        "haveTextMatchingNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveTextMatching("First V.*")
            }
            shouldAssert {
                jq("#choice option") should haveTextMatching("Not")
            }
        }

        "haveId" {
            jq("#choice option") should haveId("first")
            jq("#choice option").shouldHaveId("first")

            jq("#choice option") shouldNot haveId("other")
            jq("#choice option").shouldNotHaveId("other")
        }

        "haveIdNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveId("first")
            }
            shouldAssert {
                jq("#choice option") should haveId("other")
            }
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

        "haveClassNegative" {
            shouldAssert {
                jq("button") shouldNot haveClass("class1")
            }

            shouldAssert {
                jq("button") should haveClass("classx")
            }
        }

        "haveName" {
            jq("span.small") should haveName("name")
            jq("span.small").shouldHaveName("name")
            jq("span.small").shouldHaveName("name")

            jq("span.small") shouldNot haveName("other")
            jq("span.small").shouldNotHaveName("other")
        }

        "haveNameNegative" {
            shouldAssert {
                jq("span.small") shouldNot haveName("name")
            }
            shouldAssert {
                jq("span.small") should haveName("other")
            }
        }

        "haveValue" {
            jq("#choice option") should haveValue("first")
            jq("#choice option").shouldHaveValue("first")

            jq("#choice option") shouldNot haveValue("other")
            jq("#choice option").shouldNotHaveValue("other")
        }

        "haveValueNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveValue("first")
            }
            shouldAssert {
                jq("#choice option") should haveValue("other")
            }
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

        "haveAttributeNegative" {
            shouldAssert {
                jq("#choice option") shouldNot haveAttribute("value")
            }
            shouldAssert {
                jq("#choice option") shouldNot haveAttributeValue(
                    "value",
                    "first"
                )
            }
            shouldAssert {
                jq("#choice option") should haveAttribute("other")
            }
            shouldAssert {
                jq("#choice option") should haveAttributeValue(
                    "value",
                    "other"
                )
            }
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

        "haveTagNameNegative" {
            shouldAssert {
                jq("h1") shouldNot haveTagName("h1")
            }

            shouldAssert {
                jq("h1") should haveTagName("h2")
            }
        }

        "haveDimensionNegative" {
            shouldAssert {
                jq("h1") shouldNot haveDimension(784 to 37)
            }

            shouldAssert {
                jq("h1") should haveDimension(Dimension(100, 37))
            }
        }
    }
)
