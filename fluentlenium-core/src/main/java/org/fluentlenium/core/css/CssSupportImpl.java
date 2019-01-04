package org.fluentlenium.core.css;

import static java.util.Objects.requireNonNull;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.WebDriverException;

import org.fluentlenium.core.script.JavascriptControl;
import org.fluentlenium.core.wait.AwaitControl;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Features related to CSS loaded in the active page.
 */
public class CssSupportImpl implements CssSupport {

    private static final String INJECTOR_JS_PATH = "/org/fluentlenium/core/css/injector.js";
    private static final int MAX_SCRIPT_EXECUTION_RETRY_COUNT = 10;
    private static final long EXPLICIT_WAIT_PERIOD = 250L;

    private final JavascriptControl javascriptControl;
    private final AwaitControl awaitControl;

    /**
     * Creates a new implementation of css support
     *
     * @param javascriptControl javascript control
     * @param awaitControl      await control
     */
    public CssSupportImpl(JavascriptControl javascriptControl, AwaitControl awaitControl) {
        this.javascriptControl = requireNonNull(javascriptControl);
        this.awaitControl = requireNonNull(awaitControl);
    }

    @Override
    public void inject(String cssText) {
        cssText = cssText.replace("\r\n", "").replace("\n", "");
        cssText = StringEscapeUtils.escapeEcmaScript(cssText);
        executeScriptRetry("cssText = \"" + cssText + "\";\n" + getContentOf(INJECTOR_JS_PATH));
    }

    @Override
    public void injectResource(String cssResourceName) {
        inject(getContentOf(cssResourceName));
    }

    private String getContentOf(String resource) {
        String content;
        try (InputStream inputStream = getClass().getResourceAsStream(resource)) {
            content = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new IOError(e);
        }
        return content;
    }

    private void executeScriptRetry(String script) {
        int retries = 0;
        while (true) {
            try {
                javascriptControl.executeScript(script);
                break;
            } catch (WebDriverException e) {
                if (++retries >= MAX_SCRIPT_EXECUTION_RETRY_COUNT) {
                    throw e;
                }
                awaitControl.await().explicitlyFor(EXPLICIT_WAIT_PERIOD);
            }
        }
    }
}
