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
 * <p>
 * There is a retry logic trying to inject the CSS, using an underlying {@link org.openqa.selenium.JavascriptExecutor},
 * maximum {@link #MAX_SCRIPT_EXECUTION_RETRY_COUNT} times in case it fails, and waits for {@link #EXPLICIT_WAIT_PERIOD}
 * between each try.
 * <p>
 * The injection logic is stored in {@link #INJECTOR_JS_PATH}.
 * <p>
 * Currently neither the max retry count nor the amount of wait between each try is configurable.
 */
public class CssSupportImpl implements CssSupport {

    private static final String INJECTOR_JS_PATH = "/org/fluentlenium/core/css/injector.js";
    private static final int MAX_SCRIPT_EXECUTION_RETRY_COUNT = 10;
    private static final long EXPLICIT_WAIT_PERIOD = 250L;

    private final JavascriptControl javascriptControl;
    private final AwaitControl awaitControl;

    /**
     * Creates a new implementation of css support.
     *
     * @param javascriptControl javascript control for the injection
     * @param awaitControl      await control for waiting between injection retries
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation doesn't inject the provided resource as an external CSS {@code <link>} tag into the document,
     * rather it injects the content of the resource itself.
     */
    @Override
    public void injectResource(String cssResourceName) {
        inject(getContentOf(cssResourceName));
    }

    private String getContentOf(String resource) {
        try (InputStream inputStream = getClass().getResourceAsStream(resource)) {
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new IOError(e);
        }
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
