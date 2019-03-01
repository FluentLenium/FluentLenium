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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

/**
 * Responsible for retrieving information about Selenium version from pom.xml (if present). It logs warning when wrong
 * version is used.
 */
public final class SeleniumVersionChecker {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumVersionChecker.class);

    private static final String FAILED_TO_READ_MESSAGE =
            "Failed to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    private static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}. "
                    + "You can find example on project main page {}";

    private static final String EXPECTED_VERSION = "3.141.59";
    private static final String SELENIUM_GROUP_ID = "org.seleniumhq.selenium";
    private static final String FL_URL = "https://github.com/FluentLenium/FluentLenium";
    private static final String POM = "pom.xml";
    private static final String VERSION_REGEX = "^\\$\\{.*}$";

    private static boolean notifiedAlready;
    private static boolean isSeleniumVersionFound;


    private SeleniumVersionChecker() {
        // utility class
    }

    /**
     * Checks Selenium version during runtime.
     */
    public static void checkSeleniumVersion() {
        if (!notifiedAlready) {
            checkVersionFromMaven(POM);
        }
        notifiedAlready = true;
    }

    static void checkVersionFromMaven(String pomFile) {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;

        if (new File(pomFile).exists()) {
            model = readPom(reader, pomFile);
            logWarningsWhenSeleniumVersionIsWrong(model);
        }

        if (!isSeleniumVersionFound && new File("../" + pomFile).exists()) {
            model = readPom(reader, "../" + pomFile);
            logWarningsWhenSeleniumVersionIsWrong(model);
        }
    }

    private static void logWarningsWhenSeleniumVersionIsWrong(Model model) {
        if (model != null) {
            String seleniumVersion = retrieveVersionFromPom(model);

            if (seleniumVersion == null) {
                return;
            }
            isSeleniumVersionFound = true;
            if (checkForParametrizedVersion(seleniumVersion)) {
                seleniumVersion = checkModelForParametrizedValue(seleniumVersion, model);
            }

            if (!Objects.equals(seleniumVersion, EXPECTED_VERSION)) {
                logger.warn(WRONG_VERSION_MESSAGE, EXPECTED_VERSION, FL_URL);
            }
        }
    }

    static String retrieveVersionFromPom(Model model) {
        List<Dependency> dependencies;

        if (nonNull(model.getDependencyManagement())) {
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

    private static boolean checkForParametrizedVersion(String version) {
        return version.matches(VERSION_REGEX);
    }

    static String checkModelForParametrizedValue(String seleniumVersion, Model model) {
        String version = getNamePropertyName(seleniumVersion);

        if (nonNull(model.getProperties())) {
            return model.getProperties().getProperty(version);

        } else if (nonNull(System.getProperty(version))) {
            return System.getProperty(version);

        } else if (nonNull(model.getProfiles()) && model.getProfiles().size() > 0) {
            return getVersionNameFromProfiles(version, model);

        } else {
            return null;
        }
    }

    private static String getVersionNameFromProfiles(String version, Model model) {
        return model.getProfiles().stream()
                .filter(prof ->
                        nonNull(prof.getProperties()) && nonNull(prof.getProperties().getProperty(version)))
                .findAny()
                .map(prof -> prof.getProperties().getProperty(version))
                .orElse(null);
    }

    private static String getNamePropertyName(String propertyVersion) {
        return nonNull(propertyVersion) ? propertyVersion.substring(2, propertyVersion.length() - 1) : "";
    }

    private static Model readPom(MavenXpp3Reader reader, String pathToPom) {
        try {
            return reader.read(new FileReader(pathToPom));
        } catch (IOException | XmlPullParserException e) {
            logger.warn(FAILED_TO_READ_MESSAGE, EXPECTED_VERSION);
            return null;
        }
    }
}
