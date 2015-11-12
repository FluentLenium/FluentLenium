/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.fluentlenium.core.filter.MatcherConstructor.contains;
import static org.fluentlenium.core.filter.MatcherConstructor.endsWith;
import static org.fluentlenium.core.filter.MatcherConstructor.equal;
import static org.fluentlenium.core.filter.MatcherConstructor.notContains;
import static org.fluentlenium.core.filter.MatcherConstructor.notEndsWith;
import static org.fluentlenium.core.filter.MatcherConstructor.notStartsWith;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.fluentlenium.core.filter.MatcherConstructor.startsWith;

import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Test;

public class SelectorOnLabsTest extends SauceLabsFluentCase {

    @Test
    public void checkTagSelector() {
        goTo(DEFAULT_URL);
        assertThat($("h1")).hasSize(1);
    }

    @Test
    public void checkIdSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#oneline")).hasSize(1);
    }

    @Test
    public void checkCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasSize(3);
    }

    @Test
    public void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherCssSelectorDeprectaedStyle() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains("name")))).hasSize(2);
    }

    @Test
    public void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains(regex("na?me[0-9]*")))).getNames()).contains("name", "name2");
    }

    @Test
    public void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains(regex("na?me[0-9]*")))).getNames()).hasSize(1);
    }

    @Test
    public void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(equal("name")))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains("toto")))).hasSize(3);
    }

    @Test
    public void checkWithIdCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withId("id"))).hasSize(1);
    }

    @Test
    public void checkWithTextCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withText("Small 2"))).hasSize(1);
    }

    @Test
    public void checkSelectAttributeAction() {
        goTo(DEFAULT_URL);
        assertThat($(".small", 2).getText()).isEqualTo("Small 3");
    }

    @Test
    public void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", "true")).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", contains(regex("t?ru?")))).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("TEXT", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("text", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeDeprectaedStyle() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith("na"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("na?")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("am"))))).hasSize(0);
    }

    @Test
    public void checkEndAttributeDeprecatedStyle() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith("me")))).hasSize(1);
        assertThat($("span", withName(endsWith("name"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("na[me]*")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("am?"))))).hasSize(0);
    }

    @Test
    public void checkNotStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(notStartsWith("na"))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(notStartsWith(regex("na?")))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(notEndsWith("na"))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(notEndsWith(regex("na?")))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkWithNameMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().contains("name"))).hasSize(2);
    }

    @Test
    public void checkStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().startsWith("na")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().endsWith("me"))).hasSize(1);
        assertThat($("span", withName().endsWith("name")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkHtmlAction() {
        goTo(DEFAULT_URL);
        assertThat($("#location").first().html().equals("Pharmacy"));
    }

}
