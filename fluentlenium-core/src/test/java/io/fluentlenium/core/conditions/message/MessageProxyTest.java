package io.fluentlenium.core.conditions.message;

import io.fluentlenium.core.conditions.FluentConditions;
import io.fluentlenium.core.conditions.StringConditions;
import io.fluentlenium.core.conditions.StringConditionsImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MessageProxyTest {

    @Test
    public void testElementContains() {
        FluentConditions builder = MessageProxy.builder(FluentConditions.class, "element");
        builder.name().contains("test");

        String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("element name does not contain \"test\"");
    }

    @Test
    public void testElementNotContains() {
        FluentConditions builder = MessageProxy.builder(FluentConditions.class, "element");
        builder.name().not().contains("test");

        String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("element name contains \"test\"");
    }

    @Test
    public void testStringContainsInstance() {
        StringConditionsImpl test = spy(new StringConditionsImpl("test"));

        StringConditions builder = MessageProxy.wrap(StringConditions.class, test, "string");
        builder.contains("es");

        String message = MessageProxy.message(builder);
        Assertions.assertThat(message).isEqualTo("string does not contain \"es\"");

        verify(test).contains("es");

    }
}
