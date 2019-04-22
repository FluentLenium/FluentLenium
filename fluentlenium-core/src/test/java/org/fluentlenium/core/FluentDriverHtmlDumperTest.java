package org.fluentlenium.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.fluentlenium.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

/**
 * Unit test for {@link FluentDriverHtmlDumper}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverHtmlDumperTest {

    private static final String HTML_DUMP_CONTENT = "This is the HTML dump.";

    @Mock
    private Configuration configuration;
    private FluentDriverHtmlDumper htmlDumper;
    private File destinationFile;

    @Before
    public void setup() {
        htmlDumper = new FluentDriverHtmlDumper(configuration);
    }

    @After
    public void tearDown() {
        destinationFile.delete();
    }

    @Test
    public void shouldTakeHtmlDumpWithNoConfiguration() {
        String fileName = getSystemTempPath() + "htmldump.txt";
        destinationFile = new File(fileName);

        htmlDumper.takeHtmlDump(fileName, () -> HTML_DUMP_CONTENT);

        assertThat(destinationFile).exists().hasContent(HTML_DUMP_CONTENT);
    }

    @Test
    public void shouldTakeHtmlDumpWithConfiguration() {
        String fileName = "screenshot.png";
        destinationFile = new File(getSystemTempPath() + fileName);
        when(configuration.getHtmlDumpPath()).thenReturn(getSystemTempPath());

        htmlDumper.takeHtmlDump(fileName, () -> HTML_DUMP_CONTENT);

        assertThat(destinationFile).exists().hasContent(HTML_DUMP_CONTENT);
    }

    private String getSystemTempPath() {
        return System.getProperty("java.io.tmpdir");
    }
}
