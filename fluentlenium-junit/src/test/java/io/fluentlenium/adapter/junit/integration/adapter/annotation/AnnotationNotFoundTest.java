package io.fluentlenium.adapter.junit.integration.adapter.annotation;

import io.fluentlenium.adapter.exception.AnnotationNotFoundException;
import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnnotationNotFoundTest extends IntegrationFluentTest {

    @Test
    public void shouldThrowExceptionForClass() {
        assertThatThrownBy(() -> getClassAnnotation(CustomAnnotation.class))
                .isInstanceOf(AnnotationNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionForMethod() {
        assertThatThrownBy(() -> getMethodAnnotation(CustomAnnotation.class))
                .isInstanceOf(AnnotationNotFoundException.class);
    }

}
