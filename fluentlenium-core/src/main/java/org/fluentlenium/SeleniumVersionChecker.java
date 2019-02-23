package org.fluentlenium;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SeleniumVersionChecker {

    private static final String EXPECTED_VERSION = "3.141.59";
    private static final String SELENIUM_PROPERTY = "selenium.version";
    private static final String FL_URL = "https://github.com/FluentLenium/FluentLenium";

    private static final Logger logger = LoggerFactory.getLogger(SeleniumVersionChecker.class);

    public static void checkSeleniumVersion(){
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        if (new File("pom.xml").exists()) {
            try {
                model = reader.read(new FileReader("pom.xml"));
            } catch (IOException | XmlPullParserException e) {
                logger.warn("Failed to read Selenium version from your pom.xml");
            }
            String seleniumVersion = (String) model.getProperties().get(SELENIUM_PROPERTY);

            if (seleniumVersion == null) {
                logger.warn("You haven't defined {} property in your pom.xml. Please set it to {}. " +
                                "You can find example on project main page {}",
                        SELENIUM_PROPERTY, EXPECTED_VERSION, FL_URL);
            }

            if (seleniumVersion != null && !seleniumVersion.equals(EXPECTED_VERSION)) {
                logger.warn("You are using incompatible Selenium version. Please change it to {}",
                        EXPECTED_VERSION);
            }
        }
    }
}
