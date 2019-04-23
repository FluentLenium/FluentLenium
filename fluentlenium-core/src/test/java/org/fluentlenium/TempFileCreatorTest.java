package org.fluentlenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        destinationFile.delete();

        writeToFile(destinationFile.getAbsolutePath());

        assertThat(destinationFile).exists();
    }

    private void writeToFile(String fileName) {
        File destFile = new File(fileName);
        try {
            FileUtils.write(destFile, "Some html dump.", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
