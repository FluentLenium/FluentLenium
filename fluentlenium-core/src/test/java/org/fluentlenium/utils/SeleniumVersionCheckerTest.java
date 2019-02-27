package org.fluentlenium.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.SeleniumVersionChecker.retrieveVersionFromPom;

public class SeleniumVersionCheckerTest {

    private static final String FAILED_TO_READ_MESSAGE =
            "Failed to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    private static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}. "
                    + "You can find example on project main page {}";

    private final static String EXPECTED_VERSION = "3.141.59";
    private final static String PATH_TO_POM = "src/test/resources/org/fluentlenium/utils/";
    private final static String POM = "dummy_pom.xml";

    @Before
    public void reset() {
        SeleniumVersionChecker.isSeleniumVersionFound = false;
        SeleniumVersionChecker.notifiedAlready = false;
    }

    @Test
    public void retrieveGoodVersionShouldReturnTrueTest() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        File file = new File(PATH_TO_POM + POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveBadVersionShouldReturnNullTest() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        File file = new File(PATH_TO_POM + "wrong/" + POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNotEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveVersionShouldReturnNullWhenVersionNotPresentTest() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        File file = new File(PATH_TO_POM + "dummy/" + POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNull();
    }

    @Test
    public void shouldLogWrongVersionOfLibraryTest() {

        Logger fooLogger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        fooLogger.addAppender(listAppender);

        SeleniumVersionChecker.checkVersionFromMaven(PATH_TO_POM + "wrong/" + POM);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage)
                .hasSize(1)
                .element(0)
                .isEqualTo(WRONG_VERSION_MESSAGE);
    }

    @Test
    public void shouldLogMessageOnlyOnceTest() {

        Logger fooLogger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        fooLogger.addAppender(listAppender);

        SeleniumVersionChecker.checkVersionFromMaven(PATH_TO_POM + "wrong/" + POM);
        SeleniumVersionChecker.checkVersionFromMaven(PATH_TO_POM + "wrong/" + POM);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage)
                .hasSize(1)
                .element(0)
                .isEqualTo(WRONG_VERSION_MESSAGE);
    }

    @Test
    public void shouldNotLogAnythingWhenFileIsNotPresentTest() {

        Logger fooLogger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        fooLogger.addAppender(listAppender);

        SeleniumVersionChecker.checkVersionFromMaven(PATH_TO_POM + "wrong/not_exisitng.xml");

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage)
                .hasSize(0);
    }

    @Test
    public void shouldLogMessageWhenWrongFileWasPassedTest() {

        Logger fooLogger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        fooLogger.addAppender(listAppender);

        SeleniumVersionChecker.checkVersionFromMaven(PATH_TO_POM + "dummy_resource.json");

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage)
                .hasSize(1)
                .element(0)
                .isEqualTo(FAILED_TO_READ_MESSAGE);
    }
}