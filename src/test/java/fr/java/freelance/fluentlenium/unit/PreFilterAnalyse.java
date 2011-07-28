package fr.java.freelance.fluentlenium.unit;


import fr.java.freelance.fluentlenium.filter.Filter;
import fr.java.freelance.fluentlenium.filter.FilterType;
import fr.java.freelance.fluentlenium.filter.matcher.EqualMatcher;
import fr.java.freelance.fluentlenium.filter.matcher.Matcher;
import fr.java.freelance.fluentlenium.filter.matcher.NotContainsMatcher;
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
        Filter filter = new Filter(FilterType.ID, null, new NotContainsMatcher("toto"));
        assertThat(filter.isPreFilter()).isFalse();
    }

     @Test
    public void checkFilterIsNotPreFilterElligibleCauseText() {
        Filter filter = new Filter(FilterType.TEXT, "1");
        assertThat(filter.isPreFilter()).isFalse();
    }
}
