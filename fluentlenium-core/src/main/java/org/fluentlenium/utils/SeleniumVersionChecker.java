package org.fluentlenium.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class SeleniumVersionChecker {

    private static final String FAILED_TO_READ_MESSAGE =
            "Failed to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    private static final String NOT_SET_MESSAGE =
            "You haven't defined {} property in your pom.xml."
                    + " Please set it to {}. "
                    + "You can find example on project main page {}";
    private static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}";

    private static final String EXPECTED_VERSION = "3.141.59";
    private static final String SELENIUM_PROPERTY = "selenium.version";
    private static final String FL_URL = "https://github.com/FluentLenium/FluentLenium";
    private static final String POM = "pom.xml";

    private static final Logger logger = LoggerFactory.getLogger(SeleniumVersionChecker.class);

    private static boolean notifiedAlready;

    private SeleniumVersionChecker() {
        // utility class
    }

    public static void checkSeleniumVersion() {
        if (!notifiedAlready) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model;
            if (new File(POM).exists()) {
                try {
                    model = reader.read(new FileReader(POM));
                } catch (IOException | XmlPullParserException e) {
                    logger.warn(FAILED_TO_READ_MESSAGE, EXPECTED_VERSION);
                    model = null;
                }

                if (model != null) {
                    String seleniumVersion = (String) model.getProperties().get(SELENIUM_PROPERTY);

                    if (seleniumVersion == null) {
                        logger.warn(NOT_SET_MESSAGE, SELENIUM_PROPERTY, EXPECTED_VERSION, FL_URL);
                    }

                    if (seleniumVersion != null && !seleniumVersion.equals(EXPECTED_VERSION)) {
                        logger.warn(WRONG_VERSION_MESSAGE, EXPECTED_VERSION);
                    }
                }
            }
        }
        notifiedAlready = true;
    }
}
