package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;

import static org.fluentlenium.core.wait.FluentWaitMessages.equalToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.greaterThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.greaterThanOrEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.lessThanMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.lessThanOrEqualToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notEqualToMessage;

public class FluentSizeBuilder {

    private AbstractWaitElementMatcher parent;
    private String selection;
    private FluentWait wait;

    public FluentSizeBuilder(AbstractWaitElementMatcher parent, FluentWait fluentWait, String selection) {
        this.parent = parent;
        this.selection = selection;
        this.wait = fluentWait;
    }


    /**
     * Equals
     *
     * @param size size value
     */
    public void equalTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() == size;
            }
        };
        parent.until(wait, isPresent, equalToMessage(selection, size));
    }

    /**
     * Not equals
     *
     * @param size size value
     */
    public void notEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() != size;
            }
        };
        parent.until(wait, isPresent, notEqualToMessage(selection, size));
    }

    /**
     * Less than
     *
     * @param size size value
     */
    public void lessThan(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() < size;
            }
        };
        parent.until(wait, isPresent, lessThanMessage(selection, size));
    }

    /**
     * Less than or equals
     *
     * @param size size value
     */
    public void lessThanOrEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() <= size;
            }
        };
        parent.until(wait, isPresent, lessThanOrEqualToMessage(selection, size));
    }

    /**
     * Greater than
     *
     * @param size size value
     */
    public void greaterThan(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() > size;
            }
        };
        parent.until(wait, isPresent, greaterThanMessage(selection, size));
    }

    /**
     * Greater than or equals
     *
     * @param size size value
     */
    public void greaterThanOrEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() >= size;
            }
        };
        parent.until(wait, isPresent, greaterThanOrEqualToMessage(selection, size));
    }

    private int getSize() {
        return parent.find().size();
    }


}
