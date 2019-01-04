package org.fluentlenium.integration.localtest;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;
import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlPathFromFile;

import org.fluentlenium.integration.util.adapter.FluentTest;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

public class IntegrationFluentTest extends FluentTest {

    public static final String ANOTHER_PAGE_URL = getAbsoluteUrlFromFile("anotherpage.html");
    public static final String COMPONENTS_URL = getAbsoluteUrlFromFile("components.html");
    public static final String COUNT_URL = getAbsoluteUrlFromFile("count.html");
    public static final String DEFAULT_URL =  getAbsoluteUrlFromFile("index.html");
    public static final String DEFAULT_URL_PATH = getAbsoluteUrlPathFromFile("index.html");
    public static final String IFRAME_URL = getAbsoluteUrlFromFile("iframe.html");
    public static final String JAVASCRIPT_URL = getAbsoluteUrlFromFile("javascript.html");
    public static final String PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    public static final String PAGE_2_URL_TEST = getAbsoluteUrlFromFile("page2url.html");
    public static final String SIZE_CHANGE_URL = getAbsoluteUrlFromFile("size-change.html");


    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    public void goToSource(String htmlSource) {
        try {
            File source = File.createTempFile("source", ".tmp.html");
            try (OutputStream fos = new FileOutputStream(source)) {
                IOUtils.write(htmlSource, fos, "UTF-8");
                source.deleteOnExit();
                goTo(source.toURI().toString());
            }
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
