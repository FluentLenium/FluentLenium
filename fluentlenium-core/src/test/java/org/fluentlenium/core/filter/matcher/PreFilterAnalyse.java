package org.fluentlenium.core.filter.matcher;

import org.fluentlenium.core.filter.AttributeFilter;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PreFilterAnalyse {

    @Test
    public void checkMatcherIsPreFilterElligible() {
        final AbstractMatcher matcher = new EqualMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCausePattern() {
        final AbstractMatcher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCauseImpossible() {
        final AbstractMatcher matcher = new NotContainsMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligible() {
        final AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseMatcher() {
        final AttributeFilter filter = new AttributeFilter("id", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseText() {
        final AttributeFilter filter = new AttributeFilter("text", "1");
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseCustomAttributendMatcher() {
        final AttributeFilter filter = new AttributeFilter("ida", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttributeMatcher() {
        final AttributeFilter filter = new AttributeFilter("ida", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttribute() {
        final AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }
}
