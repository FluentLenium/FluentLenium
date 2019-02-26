package org.fluentlenium.utils;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SeleniumVersionChecker {

    private static final String FAILED_TO_READ_MESSAGE =
            "Failed to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    private static final String NOT_SET_MESSAGE =
            "Cannot find Selenium version property in your pom.xml."
                    + " Please set it to {}. "
                    + "You can find example on project main page {}";
    private static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}";

    private static final String EXPECTED_VERSION = "3.141.59";
    private static final String SELENIUM_GROUP_ID = "org.seleniumhq.selenium";
    private static final String FL_URL = "https://github.com/FluentLenium/FluentLenium";
    private static final String POM = "pom.xml";

    private static final Logger logger = LoggerFactory.getLogger(SeleniumVersionChecker.class);

    private static boolean notifiedAlready;
    private static boolean isSeleniumVersionFound;

    private SeleniumVersionChecker() {
        // utility class
    }

    public static void checkSeleniumVersion() {
        if (!notifiedAlready) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model;

            if (new File(POM).exists()) {
                model = readPom(reader, POM);
                logWarningsWhenSeleniumVersionIsWrong(model);
            }

            if (!isSeleniumVersionFound && new File("../" + POM).exists()) {
                model = readPom(reader, "../" + POM);
                logWarningsWhenSeleniumVersionIsWrong(model);
            }

            if (!isSeleniumVersionFound) {
                logger.warn(NOT_SET_MESSAGE, EXPECTED_VERSION, FL_URL);
            }
        }
        notifiedAlready = true;
    }

    private static void logWarningsWhenSeleniumVersionIsWrong(Model model) {
        System.out.println(model);
        if (model != null) {
            String seleniumVersion = retrieveVersionFromPom(model);

            if (seleniumVersion == null) {
                return;
            }
            isSeleniumVersionFound = true;

            if (!seleniumVersion.equals(EXPECTED_VERSION)) {
                logger.warn(WRONG_VERSION_MESSAGE, EXPECTED_VERSION);
            }
        }
    }

    static String retrieveVersionFromPom(Model model) {
        List<Dependency> dependencies;

        if (model.getDependencyManagement() != null) {
            dependencies = Stream.of(model.getDependencies(), model.getDependencyManagement().getDependencies())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            dependencies = model.getDependencies();
        }
        return dependencies.stream()
                .filter(dep -> dep.getGroupId().contains(SELENIUM_GROUP_ID))
                .filter(dep -> !dep.getArtifactId().contains("htmlunit-driver"))
                .filter(dep -> dep.getVersion() != null)
                .findFirst()
                .map(Dependency::getVersion)
                .orElse(null);
    }

    static Model readPom(MavenXpp3Reader reader, String pathToPom) {
        Model model;
        try {
            model = reader.read(new FileReader(pathToPom));
        } catch (IOException | XmlPullParserException e) {
            logger.warn(FAILED_TO_READ_MESSAGE, EXPECTED_VERSION);
            model = null;
        }
        return model;
    }
}
