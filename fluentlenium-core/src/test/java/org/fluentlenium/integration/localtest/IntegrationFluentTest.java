package org.fluentlenium.integration.localtest;

import org.apache.commons.io.IOUtils;
import org.fluentlenium.integration.util.adapter.FluentTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;
import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlPathFromFile;

public class IntegrationFluentTest extends FluentTest {

    public static final String DEFAULT_URL;
    public static final String DEFAULT_URL_PATH;
    public static final String JAVASCRIPT_URL;
    public static final String PAGE_2_URL;
    public static final String IFRAME_URL;
    public static final String ANOTHERPAGE_URL;
    public static final String COMPONENTS_URL;
    public static final String COUNT_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        DEFAULT_URL_PATH = getAbsoluteUrlPathFromFile("index.html");
        JAVASCRIPT_URL = getAbsoluteUrlFromFile("javascript.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
        IFRAME_URL = getAbsoluteUrlFromFile("iframe.html");
        ANOTHERPAGE_URL = getAbsoluteUrlFromFile("anotherpage.html");
        COMPONENTS_URL = getAbsoluteUrlFromFile("components.html");
        COUNT_URL = getAbsoluteUrlFromFile("count.html");
    }

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    public void goToSource(String htmlSource) {
        FileOutputStream fos = null;
        try {
            File source = File.createTempFile("source", ".tmp.html");
            fos = new FileOutputStream(source);
            IOUtils.write(htmlSource, fos, "UTF-8");
            source.deleteOnExit();
            goTo(source.toURI().toString());
        } catch (IOException e) {
            throw new IOError(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }
}
