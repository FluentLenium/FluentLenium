package fr.java.freelance.fluentlenium.filter;

import fr.java.freelance.fluentlenium.filter.matcher.Matcher;

public class Filter {
    private String attribut;
    private String name;
    private Matcher matcher;

    public Filter(FilterType filterType, String name) {
        this.attribut = filterType.name();
        this.name = name;
    }


    public Filter(FilterType filterType, Matcher matcher) {
        this.attribut = filterType.name();
        this.name = matcher.getValue();
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut.toLowerCase();
    }


    public String getName() {
        return name;
    }

    public String toString() {
        String matcherAttribute = matcher != null ? matcher.getMatcherSymbol() : "";
        return "[" + attribut.toLowerCase() + matcherAttribute + "=\"" + name + "\"]";
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public boolean isPreFilter() {
        if ((matcher == null || matcher.isPreFilter()) && FilterType.TEXT.name() != attribut) {
            return true;
        }
        return false;
    }
}