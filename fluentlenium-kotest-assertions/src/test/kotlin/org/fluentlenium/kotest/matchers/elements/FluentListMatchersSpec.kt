package org.fluentlenium.kotest.matchers.elements

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.kotest.matchers.config.MatcherBase

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
    }
)
