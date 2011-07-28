package fr.java.freelance.fluentlenium.unit;


import fr.java.freelance.fluentlenium.filter.matcher.EqualMatcher;
import fr.java.freelance.fluentlenium.filter.matcher.Matcher;
import fr.java.freelance.fluentlenium.filter.matcher.NotContainsMatcher;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

public class MatcherAnalyse {

    @Test
    public void checkIsPreFilterElligible() {
        Matcher matcher = new EqualMatcher("toto");
        assertThat(matcher.isPreFilter()).isTrue();
    }

    @Test
    public void checkIsNotPreFilterElligibleCausePattern() {
        Matcher matcher = new EqualMatcher(Pattern.compile("toto"));
        assertThat(matcher.isPreFilter()).isFalse();
    }
    @Test
       public void checkIsNotPreFilterElligibleCauseImpossible() {
           Matcher matcher = new NotContainsMatcher("toto");
           assertThat(matcher.isPreFilter()).isFalse();
       }


}
