package org.fluentlenium.core.label;

import com.google.common.base.Suppliers;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FluentLabelImplTest {
    @Mock
    private FluentWebElement element;

    @Test
    public void testFluentLabel() {
        FluentLabel<FluentWebElement> fluentLabel = new FluentLabelImpl<>(element, Suppliers.ofInstance("default"));

        Assertions.assertThat(fluentLabel.toString()).isEqualTo("default");

        fluentLabel.withLabel("another");
        Assertions.assertThat(fluentLabel.toString()).isEqualTo("another");

        fluentLabel.withLabel(null);
        Assertions.assertThat(fluentLabel.toString()).isEqualTo("default");

        fluentLabel.withLabelHint("hint1");
        Assertions.assertThat(fluentLabel.toString()).isEqualTo("default [hint1]");

        fluentLabel.withLabelHint("hint2", "hint3");
        Assertions.assertThat(fluentLabel.toString()).isEqualTo("default [hint1, hint2, hint3]");
    }
}
