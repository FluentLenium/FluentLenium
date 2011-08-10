package fr.javafreelance.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

public abstract class Matcher {
    private String value;
    private Pattern pattern;

    protected Matcher(String value) {
        this.value = value;
    }

    protected Matcher(Pattern value) {
        this.pattern = value;
    }

    public String getValue() {
        return value;
    }

    public String getMatcherSymbol() {
        return getMatcherType() != null ? getMatcherType().getCssRepresentations() : null;
    }

    public boolean isPreFilter() {
        if (pattern != null || null == getMatcherSymbol()) {
            return false;
        }
        return true;
    }

    protected Pattern getPattern() {
        return pattern;
    }

    protected abstract MatcherType getMatcherType();

    /**
     * Check if the matcher is matched given the value
     *
     * @param value
     * @return
     */
    public abstract boolean isSatisfiedBy(String value);


}
