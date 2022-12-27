package io.fluentlenium.core;

import io.fluentlenium.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Takes HTML dump.
 */
public class FluentDriverHtmlDumper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FluentDriverHtmlDumper.class);

    private final Configuration configuration;

    public FluentDriverHtmlDumper(Configuration configuration) {
        this.configuration = requireNonNull(configuration);
    }

    /**
     * Dumps the HTML provided by the html supplier to a file.
     * <p>
     * If the configuration is set with an html dump path, the argument file name will be concatenated to that, creating
     * the destination file path, otherwise the destination file will be the argument file name.
     * <p>
     * If an error occurs during taking the HTML dump, the dump file is still created, but it will contain a message
     * that HTML dump could not be taken.
     *
     * @param fileName     the file name to dump the HTML to
     * @param htmlSupplier provides the HTML snippet that should be dumped
     * @throws RuntimeException when an error occurs during dumping HTML
     */
    public void takeHtmlDump(String fileName, Supplier<String> htmlSupplier) {
        File destFile = null;
        try {
            destFile = getDestinationFile(fileName);
            FileUtils.write(destFile, htmlSupplier.get(), "UTF-8");
            LOGGER.info("Created HTML dump at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            try (PrintWriter printWriter = new PrintWriter(destFile, "UTF-8")) {
                printWriter.write("Can't dump HTML");
                printWriter.println();
                e.printStackTrace(printWriter);
            } catch (IOException ioe) {
                throw new RuntimeException("Error when dumping HTML", e); //NOPMD PreserveStackTrace
            }
        }
    }

    private File getDestinationFile(String fileName) {
        File destFile;
        if (configuration.getHtmlDumpPath() == null) {
            destFile = new File(fileName);
        } else {
            destFile = Paths.get(configuration.getHtmlDumpPath(), fileName).toFile();
        }
        return destFile;
    }
}
