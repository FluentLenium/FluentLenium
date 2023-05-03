package io.fluentlenium.core.inject;

import io.fluentlenium.core.annotation.Label;
import io.fluentlenium.core.annotation.LabelHint;
import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link LabelAnnotations}.
 */
public class LabelAnnotationsTest {

    @Test
    public void shouldSetLabelValueFromLabelAnnotation() throws NoSuchFieldException {
        LabelAnnotations labelAnnotations = createLabelAnnotationsForField("fieldWithLabel");

        assertThat(labelAnnotations.getLabel()).isEqualTo("a label value");
    }

    @Test
    public void shouldSetLabelFromClassAndFieldNameIfLabelAnnotationValueIsEmpty() throws NoSuchFieldException {
        LabelAnnotations labelAnnotations = createLabelAnnotationsForField("fieldWithEmptyLabel");

        assertThat(labelAnnotations.getLabel()).isEqualTo("DummyClass.fieldWithEmptyLabel");
    }

    @Test
    public void shouldNotSetLabelIfLabelAnnotationIsNotPresent() throws NoSuchFieldException {
        LabelAnnotations labelAnnotations = createLabelAnnotationsForField("fieldWithoutLabel");

        assertThat(labelAnnotations.getLabel()).isNull();
    }

    @Test
    public void shouldSetLabelHintIfLabelHintAnnotationIsPresent() throws NoSuchFieldException {
        LabelAnnotations labelAnnotations = createLabelAnnotationsForField("fieldWithLabelHint");

        assertThat(labelAnnotations.getLabelHints()).containsExactly("labelhint", "otherlabelhint");
    }

    @Test
    public void shouldNotSetLabelHintIfLabelHintAnnotationIsNotPresent() throws NoSuchFieldException {
        LabelAnnotations labelAnnotations = createLabelAnnotationsForField("fieldWithoutLabelHint");

        assertThat(labelAnnotations.getLabelHints()).isNull();
    }

    private LabelAnnotations createLabelAnnotationsForField(String fieldWithLabel2) throws NoSuchFieldException {
        Field fieldWithLabel = DummyClass.class.getDeclaredField(fieldWithLabel2);
        return new LabelAnnotations(fieldWithLabel);
    }

    final class DummyClass {

        @Label("a label value")
        FluentWebElement fieldWithLabel;

        @Label
        FluentWebElement fieldWithEmptyLabel;

        FluentWebElement fieldWithoutLabel;

        @LabelHint({"labelhint", "otherlabelhint"})
        FluentWebElement fieldWithLabelHint;

        FluentWebElement fieldWithoutLabelHint;
    }
}
