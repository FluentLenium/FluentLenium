package org.fluentlenium.integration;

import org.apache.commons.io.IOUtils;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlDumpPathTest extends IntegrationFluentTest {

    private final Path tempDir;

    public HtmlDumpPathTest() throws IOException {
        getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        tempDir = Files.createTempDirectory("tempfiles");
    }

    @Test
    public void checkHtmlIsDumpedInPath() throws IOException {
        goTo(DEFAULT_URL);
        getConfiguration().setHtmlDumpPath(tempDir.toFile().getAbsolutePath());

        File file = new File("test.html");
        try {
            takeHtmlDump("test.html");
            FileInputStream fis = new FileInputStream(new File(tempDir.toFile(), "test.html"));
            String html = IOUtils.toString(fis, "UTF-8");
            assertThat(html).isEqualTo(this.findFirst("html").html());
            assertThat(html).isNotEmpty();
        } finally {
            file.delete();
        }
    }
}
