package org.fluentlenium.core.filter.matcher;

import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PreFilterAnalyse {

    @Test
    public void checkMatcherIsPreFilterElligible() {
        final AbstractMacher matcher = new EqualMatcher("toto");
        assertThat(matcher.isPreFilter()).isTrue();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCausePattern() {
        final AbstractMacher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isPreFilter()).isFalse();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCauseImpossible() {
        final AbstractMacher matcher = new NotContainsMatcher("toto");
        assertThat(matcher.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligible() {
        final Filter filter = new Filter(FilterType.ID, "1");
        assertThat(filter.isPreFilter()).isTrue();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseMatcher() {
        final Filter filter = new Filter(FilterType.ID, new NotContainsMatcher("toto"));
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseText() {
        final Filter filter = new Filter(FilterType.TEXT, "1");
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseCustomAttributendMatcher() {
        final Filter filter = new Filter("ida", new NotContainsMatcher("toto"));
        assertThat(filter.isPreFilter()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttributeMatcher() {
        final Filter filter = new Filter("ida", "1");
        assertThat(filter.isPreFilter()).isTrue();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttribute() {
        final Filter filter = new Filter("id", "1");
        assertThat(filter.isPreFilter()).isTrue();
    }
}
