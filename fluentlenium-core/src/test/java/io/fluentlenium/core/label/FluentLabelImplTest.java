package io.fluentlenium.core.label;

import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FluentLabelImplTest {
    @Mock
    private FluentWebElement element;

    @Test
    public void testFluentLabel() {
        FluentLabel<FluentWebElement> fluentLabel = new FluentLabelImpl<>(element, () -> "default");

        Assertions.assertThat(fluentLabel).hasToString("default");

        fluentLabel.withLabel("another");
        Assertions.assertThat(fluentLabel).hasToString("another");

        fluentLabel.withLabel(null);
        Assertions.assertThat(fluentLabel).hasToString("default");

        fluentLabel.withLabelHint("hint1");
        Assertions.assertThat(fluentLabel).hasToString("default [hint1]");

        fluentLabel.withLabelHint("hint2", "hint3");
        Assertions.assertThat(fluentLabel).hasToString("default [hint1, hint2, hint3]");
    }
}
