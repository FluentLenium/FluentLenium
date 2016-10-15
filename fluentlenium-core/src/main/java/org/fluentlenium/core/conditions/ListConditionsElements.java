package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Get actual condition elements list.
 */
public interface ListConditionsElements {
    /**
     * Get the actual list of elements.
     *
     * @return actual list of elements on which conditions are performed.
     */
    List<? extends FluentWebElement> getActualElements();
}
