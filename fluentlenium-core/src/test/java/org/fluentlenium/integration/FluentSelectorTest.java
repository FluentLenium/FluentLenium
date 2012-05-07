/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;

public class FluentSelectorTest extends LocalFluentCase {


    @Test
    public void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }


    @Test
    public void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().contains(regex("na?me[0-9]*"))).getNames()).contains("name", "name2");
    }

    @Test
    public void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().notContains(regex("na?me[0-9]*"))).getNames()).hasSize(1);
    }

    @Test
    public void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().equalTo("name"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().notContains("toto"))).hasSize(3);
    }


    @Test
    public void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated").equalTo("true")).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated").contains(regex("t?ru?"))).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("TEXT").equalTo("Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("text").equalTo("Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().startsWith(regex("na?"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().startsWith(regex("am")))).hasSize(0);
    }


    @Test
    public void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().endsWith(regex("na[me]*"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().endsWith(regex("am?")))).hasSize(0);
    }


    @Test
    public void checkNotStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notStartsWith("na")).getIds()).contains("oneline");
    }

    @Test
    public void checkNotStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notStartsWith(regex("na?"))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notEndsWith("na")).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notEndsWith(regex("na?"))).first().getId()).isEqualTo("oneline");
    }


}
