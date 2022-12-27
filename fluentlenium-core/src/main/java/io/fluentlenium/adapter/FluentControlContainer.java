package io.fluentlenium.adapter;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentControlProvider;

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
