package org.fluentlenium.core.filter.matcher;


import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

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
