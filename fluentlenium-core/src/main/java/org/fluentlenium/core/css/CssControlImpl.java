package org.fluentlenium.core.css;

import org.fluentlenium.core.script.JavascriptControl;
import org.fluentlenium.core.wait.AwaitControl;

public class CssControlImpl implements CssControl {

    private final CssSupportImpl support;

    public CssControlImpl(JavascriptControl javascriptControl, AwaitControl awaitControl) {
        this.support = new CssSupportImpl(javascriptControl, awaitControl);
    }

    @Override
    public CssSupport css() {
        return support;
    }
}
