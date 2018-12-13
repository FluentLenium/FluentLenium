package org.fluentlenium.core.filter.matcher;

import org.fluentlenium.core.filter.AttributeFilter;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PreFilterAnalyse {

    @Test
    public void checkMatcherIsPreFilterEligible() {
        AbstractMatcher matcher = new EqualMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkMatcherIsNotPreFilterEligibleCausePattern() {
        AbstractMatcher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkMatcherIsNotPreFilterEligibleCauseImpossible() {
        AbstractMatcher matcher = new NotContainsMatcher("toto");
        assertThat(matcher.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterEligible() {
        AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsNotPreFilterEligibleCauseMatcher() {
        AttributeFilter filter = new AttributeFilter("id", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterEligibleCauseText() {
        AttributeFilter filter = new AttributeFilter("text", "1");
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsNotPreFilterEligibleCauseCustomAttributendMatcher() {
        AttributeFilter filter = new AttributeFilter("ida", new NotContainsMatcher("toto"));
        assertThat(filter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void checkFilterIsPreFilterEligibleCauseCustomAttributeMatcher() {
        AttributeFilter filter = new AttributeFilter("ida", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void checkFilterIsPreFilterEligibleCauseCustomAttribute() {
        AttributeFilter filter = new AttributeFilter("id", "1");
        assertThat(filter.isCssFilterSupported()).isTrue();
    }
}
