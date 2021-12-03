package org.fluentlenium.adapter.kotest

import io.appium.java_client.MobileBy
import io.kotest.inspectors.forOne
import io.kotest.matchers.shouldBe
import org.fluentlenium.core.filter.FilterConstructor.withName
import org.openqa.selenium.By

class ExtensionsKtTest : FluentStringSpec({

    beforeTest {
        goTo(TestConstants.DEFAULT_URL)
    }

    "jq(selector, filters)" {
        jq("span", withName("name")).texts().forOne {
            it shouldBe "Small 1"
        }
    }

    "jq(filters)" {
        jq(withName("name")).texts().forOne {
            it shouldBe "Small 1"
        }
    }

    "jq(elements)" {
        val element = driver.findElement(By.name("name"))
        jq(listOf(element)).texts().forOne {
            it shouldBe "Small 1"
        }
    }

    "jq(by,filter)" {
        jq(By.name("name"), withName("name")).texts().forOne {
            it shouldBe "Small 1"
        }
    }

    "jq(mobileBy,filter)" {
        jq(MobileBy.name("name"), withName("name")).texts().forOne {
            it shouldBe "Small 1"
        }
    }
})
