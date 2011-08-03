package fr.javafreelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class EndsWithMatcher extends Matcher {

    public EndsWithMatcher(String value) {
        super(value);
    }

    public EndsWithMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.END_WITH;
    }

    public boolean isSatisfiedBy(String o) {
        return CalculateService.endsWith(getPattern(), getValue(), o);
}

}
