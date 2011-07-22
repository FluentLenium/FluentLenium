package fr.java.freelance.fluentlenium.domain;

/**
 * All actions that can be used on the list or on a web element
 */
public interface FluentDefaultActions {
    void click();

    void clear();

    void submit();

    void text(String... text);

}
