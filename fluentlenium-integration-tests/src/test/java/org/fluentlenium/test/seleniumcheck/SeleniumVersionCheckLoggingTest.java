package org.fluentlenium.test.seleniumcheck;

import org.fluentlenium.utils.SeleniumVersionChecker;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

class SeleniumVersionCheckLoggingTest {

    @Test
    void shouldNotLogAnythingWhenCorrectVersionIsDeclared() {
        Logger fooLogger = (Logger) LoggerFactory.getLogger(SeleniumVersionChecker.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        fooLogger.addAppender(listAppender);

        SeleniumVersionChecker.checkSeleniumVersion();

        assertThat(listAppender.list)
                .size().isEqualTo(0);
    }

}
