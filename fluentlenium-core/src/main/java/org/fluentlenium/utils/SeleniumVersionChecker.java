package org.fluentlenium.utils;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * Responsible for retrieving information about Selenium version from pom.xml (if present). It logs warning when wrong
 * version is used.
 */
public final class SeleniumVersionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumVersionChecker.class);

    static final String FAILED_TO_READ_MESSAGE =
            "FluentLenium wasn't able to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}. "
                    + "You can find example on project main page {}";

    private static final String EXPECTED_VERSION = "4.2.0";
    private static final String SELENIUM_GROUP_ID = "org.seleniumhq.selenium";
    private static final String FL_URL = "https://github.com/FluentLenium/FluentLenium";
    private static final String POM = "pom.xml";
    private static final String PARENT_MODULE_POM = "../pom.xml";
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
            checkVersionFromMaven();
        }
        notifiedAlready = true;
    }

    /**
     * First, if the pom.xml exists, validates the Selenium version in it.
     * <p>
     * If the pom.xml doesn't exist or it doesn't contain a Selenium version, then it proceed to validate the parent
     * module's pom.xml.
     */
    private static void checkVersionFromMaven() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;

        if (new File(POM).exists()) {
            model = readPom(reader, POM);
            logWarningsWhenSeleniumVersionIsWrong(model);
        }

        if (!isSeleniumVersionFound && new File(PARENT_MODULE_POM).exists()) {
            model = readPom(reader, PARENT_MODULE_POM);
            logWarningsWhenSeleniumVersionIsWrong(model);
        }
    }

    /**
     * Validates the Selenium version in the provided POM Model.
     * <p>
     * It can resolve both explicit and parametrised version numbers as well.
     * <p>
     * If the resolved version doesn't equal to the {@link #EXPECTED_VERSION}, it logs an error message.
     *
     * @param model the pom.xml model
     */
    static void logWarningsWhenSeleniumVersionIsWrong(Model model) {
        if (model != null) {
            String seleniumVersion = retrieveVersionFromPom(model);
            if (seleniumVersion == null) {
                return;
            }

            isSeleniumVersionFound = true;

            String resolvedSeleniumVersion;
            if (isParametrised(seleniumVersion)) {
                resolvedSeleniumVersion = resolveParametrisedVersion(model, seleniumVersion);
            } else {
                resolvedSeleniumVersion = seleniumVersion;
            }

            if (!Objects.equals(resolvedSeleniumVersion, EXPECTED_VERSION)) {
                LOGGER.warn(WRONG_VERSION_MESSAGE, EXPECTED_VERSION, FL_URL);
            }
        }
    }

    private static String resolveParametrisedVersion(Model model, String seleniumVersion) {
        String resolvedSeleniumVersion = resolveParametrisedVersionFromPom(seleniumVersion, model);

        if (resolvedSeleniumVersion == null) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            model = readPom(reader, PARENT_MODULE_POM);
            if (model != null) {
                resolvedSeleniumVersion = resolveParametrisedVersionFromPom(seleniumVersion, model);
            }
        }
        return resolvedSeleniumVersion;
    }

    /**
     * Tries to retrieve the Selenium version from the provided POM Model.
     * <p>
     * It looks for the dependency (groupId: org.seleniumhq.selenium) in both the {@code <dependencies>} and
     * {@code <dependencyManagement>} (if exists) sections.
     * <p>
     * Any artifactId that is not {@code htmlunit-driver} is collected, and the first one's version is returned,
     * or null if there was no matching dependency.
     *
     * @param model the POM model
     * @return the actual Selenium version, or null if none is present
     */
    static String retrieveVersionFromPom(Model model) {
        List<Dependency> dependencies;

        if (nonNull(model.getDependencyManagement())) {
            dependencies = Stream.of(model.getDependencies(), model.getDependencyManagement().getDependencies())
                    .flatMap(Collection::stream)
                    .collect(toList());
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

    private static boolean isParametrised(String version) {
        return version.matches(VERSION_REGEX);
    }

    /**
     * If the Selenium version happens to be parametrised, this method tries to retrieve it from various sources,
     * in the following order:
     * <ul>
     *     <li>from Maven properties from the provided Model</li>
     *     <li>from Java system properties</li>
     *     <li>if there are Maven profiles, then from the properties sections of those profiles</li>
     * </ul>
     *
     * @param seleniumVersion the name of the Selenium version property
     * @param model           the POM model
     * @return the actual version number, or null if none found
     */
    static String resolveParametrisedVersionFromPom(String seleniumVersion, Model model) {
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

    /**
     * Reads the POM from the provided path and creates a Maven Model object from that.
     *
     * @param reader    the maven reader
     * @param pathToPom the path to the POM file
     * @return the Maven Model, or null if a problem occurred during reading the file
     */
    static Model readPom(MavenXpp3Reader reader, String pathToPom) {
        Model result = null;
        try {
            result = reader.read(Files.newBufferedReader(Paths.get(pathToPom)));
        } catch (IOException | XmlPullParserException e) {
            LOGGER.info(FAILED_TO_READ_MESSAGE, EXPECTED_VERSION);
        }
        return result;
    }
}
