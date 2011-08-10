package fr.javafreelance.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

public class EqualMatcher extends Matcher {

    public EqualMatcher(String value) {
        super(value);
    }

    public EqualMatcher(Pattern pattern) {
        super(pattern);
    }

    public MatcherType getMatcherType() {
        return MatcherType.EQUAL;
    }

    public boolean isSatisfiedBy(String o) {
        return CalculateService.equal(getPattern(), getValue(), o);
    }

}
