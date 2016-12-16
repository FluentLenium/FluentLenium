package org.fluentlenium.core.filter.matcher;

import org.fluentlenium.core.filter.AttributeFilter;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PreFilterAnalyse {

    @Test
    public void checkMatcherIsPreFilterElligible() {
        AbstractMatcher matcher = new EqualMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCausePattern() {
        AbstractMatcher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkMatcherIsNotPreFilterElligibleCauseImpossible() {
        AbstractMatcher matcher = new NotContainsMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligible() {
        AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseMatcher() {
        AttributeFilter filter = new AttributeFilter("id", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseText() {
        AttributeFilter filter = new AttributeFilter("text", "1");
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterElligibleCauseCustomAttributendMatcher() {
        AttributeFilter filter = new AttributeFilter("ida", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttributeMatcher() {
        AttributeFilter filter = new AttributeFilter("ida", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsPreFilterElligibleCauseCustomAttribute() {
        AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }
}
