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

package org.fluentlenium.unit;


import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.Matcher;
import org.fluentlenium.core.filter.matcher.NotContainsMatcher;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

public class PreFilterAnalyse {

    @Test
    public void checkMatcherIsPreFilterElligible() {
        Matcher matcher = new EqualMatcher("toto");
        assertThat(matcher.isPreFilter()).isTrue();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCausePattern() {
        Matcher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isPreFilter()).isFalse();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCauseImpossible() {
        Matcher matcher = new NotContainsMatcher("toto");
        assertThat(matcher.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligible() {
        Filter filter = new Filter(FilterType.ID, "1");
        assertThat(filter.isPreFilter()).isTrue();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseMatcher() {
        Filter filter = new Filter(FilterType.ID, new NotContainsMatcher("toto"));
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseText() {
        Filter filter = new Filter(FilterType.TEXT, "1");
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseCustomAttributendMatcher() {
        Filter filter = new Filter("ida", new NotContainsMatcher("toto"));
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttributeMatcher() {
        Filter filter = new Filter("ida", "1");
        assertThat(filter.isPreFilter()).isTrue();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttribute() {
        Filter filter = new Filter("id", "1");
        assertThat(filter.isPreFilter()).isTrue();
    }
}
