package io.fluentlenium.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import net.jcip.annotations.NotThreadSafe;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.utils.SeleniumVersionChecker.FAILED_TO_READ_MESSAGE;
import static io.fluentlenium.utils.SeleniumVersionChecker.WRONG_VERSION_MESSAGE;
import static io.fluentlenium.utils.SeleniumVersionChecker.readPom;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.MISSING_SELENIUM_POM;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.PARAMETRIZED_PARENT_CHILD_POM;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.WRONG_VERSION_POM;

@NotThreadSafe
public class SeleniumVersionCheckLoggingTest {

    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;

    @Before
    public void setup() {
        logger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void shouldWarnAboutWrongSeleniumVersion() {
        Model model = getMavenModel(WRONG_VERSION_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .containsExactly(Tuple.tuple(SeleniumVersionChecker.WRONG_VERSION_MESSAGE, Level.WARN))
                .size().isEqualTo(1);
    }

    @Test
    public void shouldNotWarnWhenVersionIsCorrect() {
        String realPom = "pom.xml";
        Model model = getMavenModel(realPom);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        assertThat(listAppender.list).size().isZero();
    }

    @Test
    public void shouldNotifyWhenSeleniumVersionNotDeclared() {
        Model model = getMavenModel(MISSING_SELENIUM_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(model);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .containsExactly(Tuple.tuple(SeleniumVersionChecker.FAILED_TO_READ_MESSAGE, Level.INFO))
                .size().isEqualTo(1);
    }

    @Test
    public void shouldNotLogWarningWhenParameterSetInParentPom() {
        Model parentModel = getMavenModel(PARAMETRIZED_PARENT_CHILD_POM);

        SeleniumVersionChecker.logWarningsWhenSeleniumVersionIsWrong(parentModel);

        assertThat(listAppender.list).isEmpty();
    }

    private Model getMavenModel(String pom) {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return SeleniumVersionChecker.readPom(reader, pom);
    }
}
