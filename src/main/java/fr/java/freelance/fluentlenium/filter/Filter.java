package fr.java.freelance.fluentlenium.filter;

public class Filter {
    private String attribut;
    private String name;
    private String matcher = "";

    public Filter(FilterType filterType, String name) {
        this.attribut = filterType.name();
        this.name = name;
    }

    public Filter(FilterType filterType, String name, String matcher) {
        this.attribut = filterType.name();
        this.name = name;
        this.matcher = matcher;
    }

    public Filter(String attribut, String name, String matcher) {
        this.attribut = attribut;
        this.name = name;
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut;
    }


    public String getName() {
        return name;
    }

    public String toString() {
        return "[" + attribut.toLowerCase() + matcher + "=" + name + "]";
    }
}