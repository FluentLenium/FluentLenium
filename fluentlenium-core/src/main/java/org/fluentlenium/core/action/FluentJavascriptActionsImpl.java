package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.script.JavascriptControl;

import java.util.function.Supplier;

/**
 * Javascript actions that can be used on the list or on a web element.
 * <p>
 * Underlying webDriver must support javascript to support those actions.
 *
 * @param <T> {@code this} object type to chain method calls
 */
public class FluentJavascriptActionsImpl<T> implements FluentJavascriptActions<T> {
    private final JavascriptControl javascript;
    private final Supplier<FluentWebElement> element;
    private final T self;

    /**
     * Creates a new fluent javascript actions.
     *
     * @param self       object that will be returned by methods
     * @param javascript javascript control
     * @param element    Supplier of element on which execute the action
     */
    public FluentJavascriptActionsImpl(T self, JavascriptControl javascript, Supplier<FluentWebElement> element) {
        this.self = self;
        this.javascript = javascript;
        this.element = element;
    }

    @Override
    public T scrollIntoView() {
        javascript.executeScript("arguments[0].scrollIntoView();", element.get().getElement());
        return self;
    }

    @Override
    public T scrollIntoView(boolean alignWithTop) {
        javascript.executeScript("arguments[0].scrollIntoView(arguments[1]);", element.get().getElement(), alignWithTop);
        return self;
    }

    @Override
    public T scrollToCenter() {
        int y = element.get().getElement().getLocation().getY();
        javascript.executeScript("window.scrollTo(0," + y + " - window.innerHeight / 2)", new Object[0]);
        return self;
    }
}
