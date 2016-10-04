package org.fluentlenium.core.conditions.message;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.conditions.StringConditionsImpl;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MessageProxyTest {

    @Test
    public void testElementContains() {
        final FluentConditions builder = MessageProxy.builder(FluentConditions.class, "element");
        builder.name().contains("test");

        final String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("element name does not contain \"test\"");
    }

    @Test
    public void testElementNotContains() {
        final FluentConditions builder = MessageProxy.builder(FluentConditions.class, "element");
        builder.name().not().contains("test");

        final String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("element name contains \"test\"");
    }

    @Test
    public void testStringContainsInstance() {
        final StringConditionsImpl test = spy(new StringConditionsImpl("test"));

        final StringConditions builder = MessageProxy.wrap(StringConditions.class, test, "string");
        builder.contains("es");

        final String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("string does not contain \"es\"");

        verify(test).contains("es");

    }
}
