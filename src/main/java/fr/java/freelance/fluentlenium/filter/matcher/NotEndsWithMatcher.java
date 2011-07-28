package fr.java.freelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class NotEndsWithMatcher extends Matcher {

    public NotEndsWithMatcher(String value) {
        super(value);
    }

    public NotEndsWithMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.NOT_END_WITH;
    }

    public boolean isSatisfiedBy(String o) {
        return !CalculateService.endsWith(getPattern(), getValue(), o);
    }

}
