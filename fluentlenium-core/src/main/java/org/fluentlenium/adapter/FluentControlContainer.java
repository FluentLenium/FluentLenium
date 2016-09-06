package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentControlProvider;

/**
 * Container for {@link FluentControl} of the actual test adapter.
 */
public interface FluentControlContainer extends FluentControlProvider {
    /**
     * Set the FluentControl for actual test.
     *
     * @param fluentControl FluentControl intialized for actual test.
     */
    void setFluentControl(FluentControl fluentControl);
}
