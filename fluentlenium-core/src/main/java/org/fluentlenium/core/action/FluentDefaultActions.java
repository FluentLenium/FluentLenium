package org.fluentlenium.core.action;

/**
 * All actions that can be used on the list or on a web element
 */
public interface FluentDefaultActions<E> {
    E click();

    E submit();

    E text(String... text);

}
