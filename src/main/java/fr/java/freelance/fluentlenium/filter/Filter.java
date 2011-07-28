package fr.java.freelance.fluentlenium.filter;

import fr.java.freelance.fluentlenium.filter.matcher.EqualMatcher;
import fr.java.freelance.fluentlenium.filter.matcher.Matcher;

public class Filter {
    private final String attribut;
    private final Matcher matcher;

    public Filter(FilterType filterType, String name) {
        this.attribut = filterType.name();
        this.matcher = new EqualMatcher(name);
    }


    public Filter(FilterType filterType, Matcher matcher) {
        this.attribut = filterType.name();
        this.matcher = matcher;
    }

    public Filter(FilterType custom, String customAttribute, String value) {
        this.attribut = customAttribute;
        this.matcher = new EqualMatcher(value);
    }


    public Filter(FilterType custom, String customAttribute, Matcher matcher) {
        this.attribut = customAttribute;
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut.toLowerCase();
    }

    public String toString() {
        String matcherAttribute = matcher != null ? matcher.getMatcherSymbol() : "";
        return "[" + attribut.toLowerCase() + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public boolean isPreFilter() {
        if ((matcher != null && matcher.isPreFilter()) && !FilterType.TEXT.name().equalsIgnoreCase(getAttribut())) {
            return true;
        }
        return false;
    }
}