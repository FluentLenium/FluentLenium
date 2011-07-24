package fr.java.freelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public class ContainsMatcher extends Matcher {

    public ContainsMatcher(String value) {
        super(value);
    }

    public ContainsMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS;
    }

    public boolean isSatisfiedBy(String o) {
        if (o == null) {
            return false;
        }
        if (getPattern() == null) {
            return getValue().contains(o);
        }
        return getPattern().matcher(o).matches();
    }

}
