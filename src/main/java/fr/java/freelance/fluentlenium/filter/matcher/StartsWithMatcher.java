package fr.java.freelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class StartsWithMatcher extends Matcher {

    public StartsWithMatcher(String value) {
        super(value);
    }

    public StartsWithMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.START_WITH;
    }

    public boolean isSatisfiedBy(String o) {
       return CalculateService.startsWith(getPattern(), getValue(), o);
    }

}
