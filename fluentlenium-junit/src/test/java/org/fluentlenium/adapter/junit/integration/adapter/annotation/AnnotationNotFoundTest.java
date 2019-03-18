package org.fluentlenium.adapter.junit.integration.adapter.annotation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fluentlenium.adapter.exception.AnnotationNotFoundException;
import org.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.Test;

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
