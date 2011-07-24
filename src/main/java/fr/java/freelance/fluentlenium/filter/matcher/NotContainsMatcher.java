package fr.java.freelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class NotContainsMatcher extends Matcher {

    public NotContainsMatcher(String value) {
        super(value);
    }

    public NotContainsMatcher(Pattern pattern) {
        super(pattern);
    }

    public MatcherType getMatcherType() {
        return MatcherType.NOT_CONTAINS;
    }

    public boolean isSatisfiedBy(String o) {
        if (o == null) {
            return true;
        }
        if (getPattern() == null) {
            return !getValue().contains(o);
        }
        return !getPattern().matcher(o).matches();
    }

}