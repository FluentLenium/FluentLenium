package org.fluentlenium.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.fluentlenium.utils.SeleniumVersionChecker.FAILED_TO_READ_MESSAGE;
import static org.fluentlenium.utils.SeleniumVersionChecker.WRONG_VERSION_MESSAGE;
import static org.fluentlenium.utils.SeleniumVersionChecker.readPom;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.MISSING_SELENIUM_POM;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.WRONG_VERSION_POM;

public class SeleniumVersionCheckLoggingTest {

    @Test
    public void shouldWarnAboutWrongSeleniumVersion() {
        Logger logger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        Model model = getMavenModel(WRONG_VERSION_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        Assertions.assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .containsExactly(Tuple.tuple(WRONG_VERSION_MESSAGE, Level.WARN))
                .size().isEqualTo(1);
    }

    @Test
    public void shouldNotWarnWhenVersionIsCorrect() {
        Logger logger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        String REAL_POM = "pom.xml";
        Model model = getMavenModel(REAL_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        Assertions.assertThat(listAppender.list)
                .size().isZero();
    }

    @Test
    public void shouldNotifyWhenSeleniumVersionNotDeclared() {
        Logger logger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        Model model = getMavenModel(MISSING_SELENIUM_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        Assertions.assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .containsExactly(Tuple.tuple(FAILED_TO_READ_MESSAGE, Level.INFO))
                .size().isEqualTo(1);
    }

    private Model getMavenModel(String pom) {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return readPom(reader, pom);
    }

}
