package org.fluentlenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for temp file creation.
 */
public class TempFileCreatorTest {

    private File destinationFile;

    @After
    public void tearDown() {
        destinationFile.delete();
    }

    @Test
    public void shouldTakeHtmlDumpWithNoConfiguration() throws IOException {
        destinationFile = File.createTempFile("htmldump.html", "");

        assertThat(destinationFile).exists();
    }
}
