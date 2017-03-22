package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.FilterConstructor.withPredicate;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

public class WaitSizeTest extends IntegrationFluentTest {

    @Test
    public void checkWithNameCssSelector() {
        goTo(getAbsoluteUrlFromFile("size-change.html"));
        await().until($(".row")).size().greaterThan(2);
    }

}

