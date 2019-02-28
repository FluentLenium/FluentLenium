package org.fluentlenium.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.SeleniumVersionChecker.checkVersionFromMaven;

public class SeleniumVersionCheckerLoggerTest extends SeleniumVersionCheckerTestConstants {

    private ThreadLocal<Logger> fooLogger = new ThreadLocal<>();

    private static final ThreadLocal<String> loggerName = new ThreadLocal<>();
    private static final ThreadLocal<ListAppender<ILoggingEvent>> listAppender = new ThreadLocal<>();

    private static final String FAILED_TO_READ_MESSAGE =
            "Failed to read Selenium version from your pom.xml."
                    + " Skipped compatibility check."
                    + " Please make sure you are using correct Selenium version - {}";
    private static final String WRONG_VERSION_MESSAGE =
            "You are using incompatible Selenium version. Please change it to {}. "
                    + "You can find example on project main page {}";

    @Before
    public void reset() {

        loggerName.set(getRandomString());
        fooLogger.set((Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class));

        listAppender.set(new ListAppender<>());

        listAppender.get().setName(loggerName.get());
        listAppender.get().start();

        fooLogger.get().addAppender(listAppender.get());

        SeleniumVersionChecker.isSeleniumVersionFound.set(false);
        SeleniumVersionChecker.notifiedAlready.set(false);
    }

    @Test
    public void shouldLogWrongVersionOfLibraryTest() {

        checkVersionFromMaven(WRONG_VERSION_POM);

        assertThat(getLogsForThread(listAppender.get()))
                .hasSize(1)
                .element(0)
                .isEqualTo(WRONG_VERSION_MESSAGE);
    }

    @Test
    public void shouldNotLogAnythingWhenFileIsNotPresentTest() {

        checkVersionFromMaven(PATH_TO_TEST_FOLDER + "/not_exisitng.xml");

        assertThat(getLogsForThread(listAppender.get()))
                .hasSize(0);
    }

    @Test
    public void shouldNotLogAnythingWhenVersionParametrizedInPomPropertiesIsCorrectTest() {

        checkVersionFromMaven(PARAMETRIZED_POM);

        assertThat(getLogsForThread(listAppender.get()))
                .hasSize(0);
    }

    @Test
    public void shouldNotLogAnythingWhenVersionParametrizedInParentPomPropertiesIsCorrectTest() {

        checkVersionFromMaven(PARAMETRIZED_CHILD_POM);

        assertThat(getLogsForThread(listAppender.get())).hasSize(0);
    }

    @Test
    public void shouldLogWrongParametrizedVersionOfLibraryTest() {

        checkVersionFromMaven(PARAMETRIZED_WRONG_VERSION_POM);

        assertThat(getLogsForThread(listAppender.get()))
                .hasSize(1)
                .element(0)
                .isEqualTo(WRONG_VERSION_MESSAGE);
    }

    @Test
    public void shouldLogMessageWhenWrongFileWasPassedTest() {

        checkVersionFromMaven(PATH_TO_TEST_FOLDER + "dummy_resource.json");

        assertThat(getLogsForThread(listAppender.get()))
                .hasSize(1)
                .element(0)
                .isEqualTo(FAILED_TO_READ_MESSAGE);
    }

    private List<String> getLogsForThread(ListAppender<ILoggingEvent> listAppender) {
        return listAppender.list.stream()
                .filter(event -> Objects.equals(event.getThreadName(), Thread.currentThread().getName()))
                .filter(event -> Objects.equals(listAppender.getName(), loggerName.get()))
                .map(ILoggingEvent::getMessage)
                .collect(Collectors.toList());
    }

    private String getRandomString() {
        return String.valueOf(new Random().nextInt());
    }
}
