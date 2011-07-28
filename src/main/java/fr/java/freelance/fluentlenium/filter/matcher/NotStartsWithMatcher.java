package fr.java.freelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class NotStartsWithMatcher extends Matcher {

    public NotStartsWithMatcher(String value) {
        super(value);
    }

    public NotStartsWithMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.NOT_START_WITH;
    }

    public boolean isSatisfiedBy(String o) {
        return CalculateService.startsWith(getPattern(), getValue(), o);
    }

}
