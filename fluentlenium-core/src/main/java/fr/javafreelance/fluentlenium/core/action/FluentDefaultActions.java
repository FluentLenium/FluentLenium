package fr.javafreelance.fluentlenium.core.action;

/**
 * All actions that can be used on the list or on a web element
 */
public interface FluentDefaultActions {
    void click();

    void clear();

    void submit();

    void text(String... text);

}
