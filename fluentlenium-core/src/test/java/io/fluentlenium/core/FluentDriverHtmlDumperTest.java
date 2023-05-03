package io.fluentlenium.core;

import io.fluentlenium.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
    public void shouldTakeHtmlDumpWithNoConfiguration() throws IOException {
        initializeDestinationFile();

        htmlDumper.takeHtmlDump(destinationFile.getAbsolutePath(), () -> HTML_DUMP_CONTENT);

        assertThat(destinationFile).exists().hasContent(HTML_DUMP_CONTENT);
    }

    @Test
    public void shouldTakeHtmlDumpWithConfiguration() throws IOException {
        initializeDestinationFile();
        when(configuration.getHtmlDumpPath()).thenReturn(destinationFile.getParent());

        htmlDumper.takeHtmlDump(destinationFile.getName(), () -> HTML_DUMP_CONTENT);

        assertThat(destinationFile).exists().hasContent(HTML_DUMP_CONTENT);
    }

    private void initializeDestinationFile() throws IOException {
        destinationFile = File.createTempFile("htmldump.html", "");
        destinationFile.delete();
    }
}
