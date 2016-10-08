package org.fluentlenium.core.css;

import org.fluentlenium.core.script.JavascriptControl;
import org.fluentlenium.core.wait.AwaitControl;

/**
 * Control interface for css related features.
 */
public class CssControlImpl implements CssControl {

    private final CssSupportImpl support;

    /**
     * Creates a new css control implementation
     *
     * @param javascriptControl javascript control
     * @param awaitControl      await control
     */
    public CssControlImpl(final JavascriptControl javascriptControl, final AwaitControl awaitControl) {
        this.support = new CssSupportImpl(javascriptControl, awaitControl);
    }

    @Override
    public CssSupport css() {
        return support;
    }
}
